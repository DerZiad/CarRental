package com.coding.app.models;

import com.coding.app.models.enums.Brand;
import com.coding.app.models.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "cars")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int year;

    private double price;

    private String fileName;

    @Lob
    @NotNull(message = "At least one image is required")
    @JsonIgnore
    private byte[] image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car", targetEntity = Reservation.class)
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    public String getBase64Image() {
        if (this.image != null && this.image.length != 0) {
            return Base64.getEncoder().encodeToString(this.image);
        }
        return "";
    }
}
