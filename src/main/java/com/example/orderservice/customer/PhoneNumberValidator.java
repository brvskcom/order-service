package com.example.orderservice.customer;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(\\+\\d{2})?\\d{7,13}");

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return true;
        }
        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }
}
