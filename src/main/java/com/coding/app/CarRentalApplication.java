package com.coding.app;

import com.coding.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CarRentalApplication implements CommandLineRunner {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userService.adminExists()) {
            userService.createAdmin();
        }
    }
}