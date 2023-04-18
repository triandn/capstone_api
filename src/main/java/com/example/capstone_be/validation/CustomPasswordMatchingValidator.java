package com.example.capstone_be.validation;

import com.example.capstone_be.dto.user.UserDto;
import com.example.capstone_be.dto.user.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomPasswordMatchingValidator implements ConstraintValidator<PasswordMatching, UserRegistrationDto>{
    @Override
    public void initialize(PasswordMatching constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserRegistrationDto value, ConstraintValidatorContext context) {
        return value.getUserPassword().equals(value.getMatchingPassword());
    }
}
