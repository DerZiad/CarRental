package com.coding.app.controllers;

import com.coding.app.exceptions.NotFoundException;
import com.coding.app.models.Car;
import com.coding.app.models.User;
import com.coding.app.repository.CarRepository;
import com.coding.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.coding.app.controllers.DashboardViewAttributes.CURRENT_USER;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ClientPortalController {
	
	private final static String PATH_CLIENT = "index";
	private final static String ATTRIBUT_VOITURES = "cars";

	private final UserService userService;

	@Autowired
	private CarRepository carRepository;
	
	@GetMapping
	public ModelAndView getString() {
		ModelAndView model = new ModelAndView(PATH_CLIENT);		
		configureCurrentUser(model);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			model.addObject("username",auth.getName());
		}
		
		List<Car> cars = carRepository.findAll();
		if(cars.size() < 6) {
			model.addObject(ATTRIBUT_VOITURES, cars);
		}else {
			List<Car> sortedCars = new ArrayList<Car>();
			for (int i = 0; i < 6; i++) {
				sortedCars.add(cars.get(i));
			}
			model.addObject(ATTRIBUT_VOITURES, sortedCars);
		}
		
		return model;
	}

	private void configureCurrentUser(ModelAndView model) {
        try {
			final User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			model.addObject(CURRENT_USER, user);
        } catch (NotFoundException ignored) {
        }
	}
}
