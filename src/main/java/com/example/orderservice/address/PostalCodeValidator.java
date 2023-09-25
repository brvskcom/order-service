package com.example.orderservice.address;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {

    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("\\d{2}-\\d{3}");

    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext context) {
        if (postalCode == null) {
            return true;
        }
        return POSTAL_CODE_PATTERN.matcher(postalCode).matches();
    }
}
