package com.coding.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationRequest {

    private String username;

    private Long carId;

    private String startDate;

    private String endDate;
}
