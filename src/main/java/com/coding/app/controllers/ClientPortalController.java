package com.coding.app.controllers;

import com.coding.app.dto.ReservationRequest;
import com.coding.app.exceptions.NotFoundException;
import com.coding.app.exceptions.ReservationException;
import com.coding.app.models.Car;
import com.coding.app.models.User;
import com.coding.app.services.CarService;
import com.coding.app.services.ClientPortalService;
import com.coding.app.services.ReservationService;
import com.coding.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.coding.app.controllers.DashboardViewAttributes.CURRENT_USER;

@Controller
@RequiredArgsConstructor
public class ClientPortalController {

    private final static String JSP_CLIENT_PORTAL = "index";
    private final static String ATTR_CARS = "cars";

    private final UserService userService;
    private final CarService carService;

    @GetMapping("/")
    public ModelAndView getClientIndexPage() {
        final ModelAndView model = new ModelAndView(JSP_CLIENT_PORTAL);
        configureCurrentUser(model);
        List<Car> cars = carService.getAllCars();
        if (cars.size() > 6) {
            cars = cars.subList(0, 6);
        }
        model.addObject(ATTR_CARS, cars);
        return model;
    }

    private final ClientPortalService clientPortalService;
    private final ReservationService reservationService;

    @GetMapping("/api/cars")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public HttpEntity<?> getCars() {
        return ResponseEntity.ok(clientPortalService.getAllCars());
    }

    @GetMapping("/api/cars/{carId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public HttpEntity<?> getCarById(@PathVariable final Long carId) throws NotFoundException {
        return ResponseEntity.ok(clientPortalService.getCarById(carId));
    }

    @PostMapping("/api/reserve")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    public HttpEntity<?> reserve(@RequestBody ReservationRequest reservationRequest) throws ReservationException, NotFoundException {
        reservationService.createReservation(reservationRequest);
        return ResponseEntity.accepted().build();
    }

    private void configureCurrentUser(ModelAndView model) {
        try {
            final User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addObject(CURRENT_USER, user);
        } catch (NotFoundException ignored) {
        }
    }
}
