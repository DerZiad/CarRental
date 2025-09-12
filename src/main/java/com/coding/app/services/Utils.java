package com.coding.app.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Cleanup;

import java.util.HashMap;
import java.util.Set;

public interface Utils {

    static <T> HashMap<String, String> validate(T object) {
        final HashMap<String, String> errors = new HashMap<String, String>();
        @Cleanup
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();
        final Set<ConstraintViolation<T>> violatons = validator.validate(object);
        for (ConstraintViolation<T> constraintViolation : violatons) {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return errors;
    }
}
