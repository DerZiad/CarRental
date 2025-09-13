package com.coding.app.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {

    CITY_CAR("City Car"),
    SEDAN("Sedan"),
    STATION_WAGON("Station Wagon"),
    MINIVAN("Minivan"),
    SUV("SUV"),
    COUPE("Coupe"),
    CONVERTIBLE("Convertible"),
    PICKUP("Pickup");

    private final String displayName;
}
