package com.coding.app.dto;

import com.coding.app.models.User;
import lombok.Builder;

import java.util.HashMap;

@Builder
public record DashboardData(Long totalUsers, Long totalCars, Long totalReservations, Long totalCompletedReservations,
                            Long totalInProgressReservations, Long totalGainOfTheMonth,
                            HashMap<User, Integer> topFiveUsersByReservations) {

}
