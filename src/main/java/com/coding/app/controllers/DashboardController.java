package com.coding.app.controllers;

import static com.coding.app.controllers.CarViewAttributes.ATTR_BRANDS;
import static com.coding.app.controllers.CarViewAttributes.ATTR_CAR;
import static com.coding.app.controllers.CarViewAttributes.ATTR_CARS;
import static com.coding.app.controllers.CarViewAttributes.ATTR_CATEGORIES;
import static com.coding.app.controllers.CarViewAttributes.ATTR_ERRORS;
import static com.coding.app.controllers.CarViewAttributes.ATTR_YEARS;
import static com.coding.app.controllers.CarViewAttributes.CAR_URI;
import static com.coding.app.controllers.CarViewAttributes.PAGE_CAR;
import static com.coding.app.controllers.CarViewAttributes.REDIRECT_CAR;
import static com.coding.app.controllers.DashboardViewAttributes.CURRENT_USER;
import static com.coding.app.controllers.DashboardViewAttributes.JSP_SHARED_DASHBOARD;
import static com.coding.app.controllers.DashboardViewAttributes.SHARED_URI;
import static com.coding.app.controllers.ReservationViewAttributes.JSP_ADMIN_RESERVATION;
import static com.coding.app.controllers.ReservationViewAttributes.*;
import static com.coding.app.controllers.ReservationViewAttributes.SHARED_RESERVATION_URI;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import com.coding.app.dto.CarRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import com.coding.app.exceptions.InvalidObjectException;
import com.coding.app.exceptions.NotFoundException;
import com.coding.app.models.Car;
import com.coding.app.models.Reservation;
import com.coding.app.models.User;
import com.coding.app.models.enums.Brand;
import com.coding.app.models.enums.Category;
import com.coding.app.services.CarService;
import com.coding.app.services.ReservationService;
import com.coding.app.services.UserService;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class DashboardViewAttributes {

    public static final String SHARED_URI = "/shared";
    public static final String JSP_SHARED_DASHBOARD = "dashboard-portal/index";
    public static final String CURRENT_USER = "user";
}

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ReservationViewAttributes {

    public static final String SHARED_RESERVATION_URI = "/shared/reservation";
    public static final String JSP_ADMIN_RESERVATION = "dashboard-portal/page_reservation";
    public static final String REDIRECT_RESERVATION = "redirect:/manager/reservation";
    public final static String MODEL_AND_VIEW_RESERVATIONS_ATTRIBUTE = "reservations";
}

@NoArgsConstructor
class CarViewAttributes {

    public static final String CAR_URI = "/shared/car";
    public static final String PAGE_CAR = "dashboard-portal/page_car";
    public static final String REDIRECT_CAR = "redirect:" + CAR_URI;

    public static final String ATTR_BRANDS = "brands";
    public static final String ATTR_CATEGORIES = "categories";
    public static final String ATTR_YEARS = "years";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_CAR = "car";
    public static final String ATTR_CARS = "cars";
}

@Controller
@RequiredArgsConstructor
public final class DashboardController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final CarService carService;

    @GetMapping(CAR_URI)
    public ModelAndView showCarPage() {
        return getPageCar();
    }

    @PostMapping(CAR_URI)
    public ModelAndView createCar(
        @ModelAttribute CarRequest car,
        @RequestParam(value = "partFile", required = false) MultipartFile imageFile
    ) {

        ModelAndView model;
        try {
            carService.addCar(car,imageFile);
            model = new ModelAndView(REDIRECT_CAR);
        } catch (final InvalidObjectException e) {
            model = getPageCar();
            model.addObject(ATTR_ERRORS, e.getErrors());
            model.addObject(ATTR_CAR, car);
        }
        return model;
    }

    @GetMapping(CAR_URI + "/{carId}")
    public ModelAndView showCarById(@PathVariable("carId") final Long carId) throws NotFoundException {
        final Car car = carService.getCar(carId);
        final ModelAndView model = getPageCar();
        model.addObject("modification", true);
        model.addObject(ATTR_CAR, car);
        return model;
    }

    @PostMapping(CAR_URI + "/{carId}")
    public ModelAndView updateCarById(@RequestPayload final Car car) {
        ModelAndView model;
        try {
            carService.updateCar(car);
            model = new ModelAndView(REDIRECT_CAR);
        } catch (final InvalidObjectException e) {
            model = getPageCar();
            model.addObject(ATTR_ERRORS, e.getErrors());
            model.addObject(ATTR_CAR, car);
        }
        return model;
    }

    @GetMapping(CAR_URI + "/delete/{carId}")
    public ModelAndView deleteCar(@PathVariable("carId") final Long carId) throws NotFoundException {
        carService.deleteCar(carId);
        return new ModelAndView(REDIRECT_CAR);
    }

    @GetMapping(SHARED_URI)
    public ModelAndView getDashboard() {
        final ModelAndView modelAndView = new ModelAndView(JSP_SHARED_DASHBOARD);
        configureCurrentUser(modelAndView);
        DashboardUtils.activateMenu(DashboardUtils.NavbarMenu.DASHBOARD, modelAndView);
        return modelAndView;
    }

    @GetMapping(SHARED_RESERVATION_URI)
    public ModelAndView getReservationPage() {
        final ModelAndView model = new ModelAndView(JSP_ADMIN_RESERVATION);
        configureCurrentUser(model);
        DashboardUtils.activateMenu(DashboardUtils.NavbarMenu.RESERVATIONS, model);
        final List<Reservation> reservations = reservationService.getAllReservations();
        model.addObject(MODEL_AND_VIEW_RESERVATIONS_ATTRIBUTE, reservations);
        return model;
    }

    @GetMapping(SHARED_RESERVATION_URI + "/accept/{cardId}/{username}")
    public ModelAndView acceptReservation(@PathVariable("cardId") final Long cardId, @PathVariable("username") final String username) throws NotFoundException {
        reservationService.acceptReservation(cardId, username);
        return new ModelAndView(REDIRECT_RESERVATION);
    }

    @GetMapping(SHARED_RESERVATION_URI + "/delete/{cardId}/{username}")
    public ModelAndView deleteReservation(@PathVariable("cardId") final Long cardId, @PathVariable("username") final String username) throws NotFoundException {
        reservationService.deleteReservation(cardId, username);
        return new ModelAndView(REDIRECT_RESERVATION);
    }

    // Privates

    private ModelAndView getPageCar(){
        final ModelAndView model = new ModelAndView(PAGE_CAR);
        configureCurrentUser(model);
        DashboardUtils.activateMenu(DashboardUtils.NavbarMenu.CAR, model);
        model.addObject(ATTR_CATEGORIES, Category.values());
        model.addObject(ATTR_BRANDS, Brand.values());
        model.addObject(ATTR_YEARS, getYearList());
        model.addObject(ATTR_CARS, carService.getAllCars());
        return model;
    }

    private void configureCurrentUser(final ModelAndView model) {
        try {
            final User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addObject(CURRENT_USER, user);
        } catch (final NotFoundException e) {
            model.setViewName("redirect:/");
        }
    }

    private static List<Integer> getYearList() {
        final List<Integer> years = new ArrayList<>();
        final int currentYear = Year.now().getValue();
        for (int i = 1999; i <= currentYear; i++) {
            years.add(i);
        }
        return years;
    }
}
