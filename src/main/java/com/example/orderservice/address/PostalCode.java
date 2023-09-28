package com.example.orderservice.address;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PostalCodeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostalCode {
    String message() default "Invalid postal code format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}