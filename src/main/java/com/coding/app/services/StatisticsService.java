package com.coding.app.services;

import com.coding.app.dto.CarResponse;
import com.coding.app.dto.DashboardData;
import com.coding.app.models.Reservation;
import com.coding.app.models.User;
import com.coding.app.repository.CarRepository;
import com.coding.app.repository.ReservationRepository;
import com.coding.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public DashboardData getDashboardData() {
        final Long totalUsers = userRepository.count() - 1L;
        final Long totalCars = carRepository.count();
        final List<Reservation> reservations = reservationRepository.findAll();
        final Long totalReservations = (long) reservations.size();
        final Long totalCompletedReservations = reservations.stream().filter(Reservation::completed).count();
        final Long totalInProgressReservations = reservations.stream().filter(Reservation::isOngoing).count();

        return null;
    }

    /*public Long getTotalGainOfTheMonth() {
        final Date firstMonth = Date.valueOf(java.time.LocalDate.now().withDayOfMonth(1));
        final Date lastMonth = Date.valueOf(java.time.LocalDate.now().withDayOfMonth(java.time.LocalDate.now().lengthOfMonth()));
        return reservationRepository.findAll().stream()
                .filter(reservation -> reservation.getStartDay().after(firstMonth) && reservation.getEndDay().before(lastMonth))
                .mapToLong(reservation -> reservation.getCar().getPrice() * (reservation.getEndDay().getTime() - reservation.getStartDay().getTime()) / (1000 * 60 * 60 * 24))
                .sum();
    }*/
}
