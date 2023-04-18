package com.example.capstone_be.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CustomEmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.compile(EMAIL_PATTERN)
                .matcher(email)
                .matches();
    }
}
