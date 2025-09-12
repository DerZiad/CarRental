package com.coding.app.controllers;

import com.coding.app.dto.ReservationRequest;
import com.coding.app.exceptions.NotFoundException;
import com.coding.app.exceptions.ReservationException;
import com.coding.app.services.ClientPortalService;
import com.coding.app.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ClientPortalRestController {

    private final ClientPortalService clientPortalService;
    private final ReservationService reservationService;

    @GetMapping("/cars")
    public HttpEntity<?> getCars() {
        return ResponseEntity.ok(clientPortalService.getAllCars());
    }

    @GetMapping("/cars/{carId}")
    public HttpEntity<?> getCarById(@PathVariable final Long carId) throws NotFoundException {
        return ResponseEntity.ok(clientPortalService.getCarById(carId));
    }

	@PostMapping("/reserve")
	public HttpEntity<?> reserve(@RequestBody ReservationRequest reservationRequest) throws ReservationException, NotFoundException {
        reservationService.createReservation(reservationRequest);
        return ResponseEntity.accepted().build();
	}
}
