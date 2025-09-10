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

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final CarService carService;

    public Reservation createReservation(final ReservationRequest reservationRequest) throws NotFoundException {
        final Car car = carService.getCar(reservationRequest.getCarId());
        final Date startDate = convertToDate(reservationRequest.getStartDate());
        final Date endDate = convertToDate(reservationRequest.getEndDate());

        if (isReserved(car.getId(), startDate, endDate)) {
            throw new NotFoundException("Car is already reserved for the selected dates");
        }

        final User user = userService.findByUsername(reservationRequest.getUsername());

        final Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setUser(user);
        reservation.setConfirmed(false);
        reservation.setStartDay(startDate);
        reservation.setEndDay(endDate);

        return reservationRepository.save(reservation);
    }

    public boolean isReserved(final Long carId, final Date startDate, final Date endDate) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getCar().getId().equals(carId))
                .anyMatch(r -> datesOverlap(r.getStartDay(), r.getEndDay(), startDate, endDate));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByFilter(final Predicate<Reservation> filter) {
        return reservationRepository.findAll().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public void acceptReservation(final Long carId, final String username) throws NotFoundException {
        final Reservation reservation = getReservationOrThrow(carId, username);
        reservation.setConfirmed(true);
        reservationRepository.save(reservation);

        final User user = userService.findByUsername(username);
        final Car car = carService.getCar(carId);

        sendEmailConfirmation(user.getEmail(), buildEmailBody(user, car, "accepted"));
    }

    public void deleteReservation(final Long carId, final String username) throws NotFoundException {
        final Reservation reservation = getReservationOrThrow(carId, username);
        reservationRepository.delete(reservation);

        final User user = userService.findByUsername(username);
        final Car car = carService.getCar(carId);

        sendEmailConfirmation(user.getEmail(), buildEmailBody(user, car, "canceled"));
    }

    private Reservation getReservationOrThrow(Long carId, String username) throws NotFoundException {
        final KeyReservation id = new KeyReservation(username, carId);
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));
    }

    private Map<String, String> buildEmailBody(User user, Car car, String action) {
        return Map.of(
                "name", user.getUsername(),
                "informationText", "Your reservation for the vehicle " +
                        car.getBrand().name() + " (" + car.getCategory() + ") has been " + action + "."
        );
    }

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

    private Date convertToDate(final String toParse) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date utilDate = sdf.parse(toParse);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + toParse, e);
        }
    }

    private boolean datesOverlap(final Date existingStart, final Date existingEnd, final Date requestedStart, final Date requestedEnd) {
        return !requestedEnd.before(existingStart) && !requestedStart.after(existingEnd);
    }
}
