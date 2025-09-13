package com.coding.app.dto;

import com.coding.app.models.Car;

public record CarResponse(String brand, String category, double price, int year, String base64Image) {

    public static CarResponse toCarResponse(final Car car) {
        return new CarResponse(
                car.getBrand().getDisplayName(),
                car.getCategory().getDisplayName(),
                car.getPrice(),
                car.getYear(),
                car.getBase64Image()
        );
    }
}
