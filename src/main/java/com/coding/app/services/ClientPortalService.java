package com.coding.app.services;

import com.coding.app.dto.CarResponse;
import com.coding.app.exceptions.NotFoundException;
import com.coding.app.models.Reservation;
import com.coding.app.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for client portal operations, such as retrieving cars and reservations.
 */
@Service
@RequiredArgsConstructor
public class ClientPortalService {

    private final CarRepository carRepository;

    /**
     * Retrieves all cars as CarResponse DTOs.
     *
     * @return List of CarResponse objects.
     */
    public List<CarResponse> getAllCars() {
        return this.carRepository.findAll().stream().map(CarResponse::toCarResponse).toList();
    }

    /**
     * Retrieves a car by its ID as a CarResponse DTO.
     *
     * @param carId The ID of the car.
     * @return The CarResponse object.
     * @throws NotFoundException if the car is not found.
     */
    public CarResponse getCarById(final Long carId) throws NotFoundException {
        return CarResponse.toCarResponse(this.carRepository.findById(carId).orElseThrow(() -> new NotFoundException("Car not found")));
    }

    /**
     * Retrieves reservations for a car during a specific period.
     *
     * @param carId     The car ID.
     * @param startDate The start date.
     * @param endDate   The end date.
     * @return List of Reservation objects (currently not implemented).
     */
    public List<Reservation> getReservationDuringPeriodForCar(final Long carId, final String startDate, final String endDate) {

        return null; // To be implemented
    }
}
