package com.example.capstone_be.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Target({TYPE, FIELD, PARAMETER, RECORD_COMPONENT})
@Constraint(validatedBy = CustomPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must be at least 8 characters long, contains at least 1 upper case letter, 1 lower case letter, and 1 digit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
