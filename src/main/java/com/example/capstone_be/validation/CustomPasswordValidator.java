package com.example.capstone_be.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CustomPasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile(PASSWORD_PATTERN)
                .matcher(value)
                .matches();
    }
}
