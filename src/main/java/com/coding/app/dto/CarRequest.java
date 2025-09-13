package com.coding.app.dto;

import com.coding.app.models.enums.Brand;
import com.coding.app.models.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class CarRequest {

    private Brand brand;

    private Category category;

    private int year;

    private double price;
}
