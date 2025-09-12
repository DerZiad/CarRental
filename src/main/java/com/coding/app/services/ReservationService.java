package com.coding.app.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.coding.app.dto.ReservationRequest;
import com.coding.app.exceptions.ReservationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.coding.app.exceptions.NotFoundException;
import com.coding.app.models.Car;
import com.coding.app.models.Reservation;
import com.coding.app.models.User;
import com.coding.app.models.enums.EmailType;
import com.coding.app.models.key.KeyReservation;
import com.coding.app.repository.ReservationRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

/**
 * Service for managing car reservations, including creation, confirmation, and deletion.
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final CarService carService;

    /**
     * Creates a new reservation for a car.
     *
     * @param reservationRequest The reservation request DTO.
     * @return The created Reservation.
     * @throws ReservationException if the car is already reserved.
     * @throws NotFoundException    if the car or user is not found.
     */
    public Reservation createReservation(final ReservationRequest reservationRequest) throws ReservationException, NotFoundException {
        final Car car = carService.getCar(reservationRequest.getCarId());
        final Date startDate = convertToDate(reservationRequest.getStartDate());
        final Date endDate = convertToDate(reservationRequest.getEndDate());
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (isReserved(car.getId(), startDate, endDate)) {
            throw new ReservationException("Car is already reserved for the selected dates");
        }

        final User user = userService.findByUsername(username);

        final Reservation reservation = new Reservation();
        final KeyReservation keyReservation = new KeyReservation(username, car.getId());
        reservation.setId(keyReservation);
        reservation.setCar(car);
        reservation.setUser(user);
        reservation.setConfirmed(false);
        reservation.setStartDay(startDate);
        reservation.setEndDay(endDate);

        return reservationRepository.save(reservation);
    }

    /**
     * Checks if a car is reserved for the given date range.
     *
     * @param carId     The car ID.
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return true if reserved, false otherwise.
     */
    public boolean isReserved(final Long carId, final Date startDate, final Date endDate) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getCar().getId().equals(carId))
                .anyMatch(r -> datesOverlap(r.getStartDay(), r.getEndDay(), startDate, endDate));
    }

    /**
     * Retrieves all reservations.
     *
     * @return List of Reservation objects.
     */
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Retrieves reservations filtered by a predicate.
     *
     * @param filter The predicate to filter reservations.
     * @return List of filtered Reservation objects.
     */
    public List<Reservation> getReservationsByFilter(final Predicate<Reservation> filter) {
        return reservationRepository.findAll().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    /**
     * Accepts (confirms) a reservation for a car and user.
     *
     * @param carId   The car ID.
     * @param username The username.
     * @throws NotFoundException if reservation, user, or car is not found.
     */
    public void acceptReservation(final Long carId, final String username) throws NotFoundException {
        final Reservation reservation = getReservationOrThrow(carId, username);
        reservation.setConfirmed(true);
        reservationRepository.save(reservation);

        final User user = userService.findByUsername(username);
        final Car car = carService.getCar(carId);

        sendEmailConfirmation(user.getEmail(), buildEmailBody(user, car, "accepted"));
    }

    /**
     * Deletes a reservation for a car and user.
     *
     * @param carId   The car ID.
     * @param username The username.
     * @throws NotFoundException if reservation, user, or car is not found.
     */
    public void deleteReservation(final Long carId, final String username) throws NotFoundException {
        final Reservation reservation = getReservationOrThrow(carId, username);
        reservationRepository.delete(reservation);

        final User user = userService.findByUsername(username);
        final Car car = carService.getCar(carId);

        sendEmailConfirmation(user.getEmail(), buildEmailBody(user, car, "canceled"));
    }

    /**
     * Retrieves a reservation by car ID and username or throws if not found.
     *
     * @param carId   The car ID.
     * @param username The username.
     * @return The Reservation object.
     * @throws NotFoundException if not found.
     */
    private Reservation getReservationOrThrow(Long carId, String username) throws NotFoundException {
        final KeyReservation id = new KeyReservation(username, carId);
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
    }

    /**
     * Builds the email body for reservation confirmation or cancellation.
     *
     * @param user   The user.
     * @param car    The car.
     * @param action The action performed.
     * @return Map of email body fields.
     */
    private Map<String, String> buildEmailBody(User user, Car car, String action) {
        return Map.of(
                "name", user.getUsername(),
                "informationText", "Your reservation for the vehicle " +
                        car.getBrand().name() + " (" + car.getCategory() + ") has been " + action + "."
        );
    }

    /**
     * Sends an email confirmation in a separate thread.
     *
     * @param to        Recipient email.
     * @param letterBody Email body fields.
     */
    private void sendEmailConfirmation(final String to, final Map<String, String> letterBody) {
        new Thread(() -> {
            final EmailService.Email email =
                    new EmailService.Email(to, "Reservation Update", EmailType.INFORMATION, letterBody);
            try {
                emailService.sendEmail(email);
            } catch (final MessagingException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Converts a string to SQL Date.
     *
     * @param toParse The date string.
     * @return The SQL Date.
     */
    private Date convertToDate(final String toParse) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date utilDate = sdf.parse(toParse + " 11:00:00");
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + toParse, e);
        }
    }

    /**
     * Checks if two date ranges overlap.
     *
     * @param existingStart   Existing reservation start date.
     * @param existingEnd     Existing reservation end date.
     * @param requestedStart  Requested reservation start date.
     * @param requestedEnd    Requested reservation end date.
     * @return true if overlap, false otherwise.
     */
    private boolean datesOverlap(final Date existingStart, final Date existingEnd, final Date requestedStart, final Date requestedEnd) {
        return !requestedEnd.before(existingStart) && !requestedStart.after(existingEnd);
    }
}
