package com.coding.app.services;

import com.coding.app.dto.DashboardData;
import com.coding.app.models.Reservation;
import com.coding.app.models.User;
import com.coding.app.repository.CarRepository;
import com.coding.app.repository.ReservationRepository;
import com.coding.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public DashboardData getDashboardData() {
        final List<Reservation> reservations = reservationRepository.findAll();

        return DashboardData.builder()
                .totalUsers(getTotalUsers())
                .totalCars(getTotalCars())
                .totalReservations((long) reservations.size())
                .totalCompletedReservations(getCompletedReservationsCount(reservations))
                .totalInProgressReservations(getInProgressReservationsCount(reservations))
                .totalGainOfTheMonth(getTotalGainOfTheMonth(reservations))
                .topFiveUsersByReservations(getTopFiveUsersByReservations(reservations))
                .build();
    }

    private Long getTotalUsers() {
        final long adminCount = 1L;
        return userRepository.count() - adminCount;
    }

    private Long getTotalCars() {
        return carRepository.count();
    }

    private Long getCompletedReservationsCount(List<Reservation> reservations) {
        return reservations.stream().filter(Reservation::completed).count();
    }

    private Long getInProgressReservationsCount(List<Reservation> reservations) {
        return reservations.stream().filter(Reservation::isOngoing).count();
    }

    private Long getTotalGainOfTheMonth(List<Reservation> reservations) {
        LocalDate now = LocalDate.now();
        Date firstDayOfMonth = Date.valueOf(now.withDayOfMonth(1));
        Date lastDayOfMonth = Date.valueOf(now.withDayOfMonth(now.lengthOfMonth()));

        return reservations.stream()
                .filter(reservation -> !reservation.getStartDay().before(firstDayOfMonth) && !reservation.getEndDay().after(lastDayOfMonth))
                .mapToLong(reservation -> {
                    long days = (reservation.getEndDay().getTime() - reservation.getStartDay().getTime()) / (1000 * 60 * 60 * 24) + 1;
                    return (long) (reservation.getCar().getPrice() * days);
                })
                .sum();
    }

    private HashMap<User, Integer> getTopFiveUsersByReservations(final List<Reservation> reservations) {
        final HashMap<User, Integer> countMap = new HashMap<>();
        reservations.forEach(reservation -> countMap.merge(reservation.getUser(), 1, Integer::sum));
        return countMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
    }
}
