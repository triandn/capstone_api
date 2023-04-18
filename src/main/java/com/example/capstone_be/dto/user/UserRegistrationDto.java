package com.example.capstone_be.dto.user;


import com.example.capstone_be.validation.PasswordMatching;
import com.example.capstone_be.validation.ValidEmail;
import com.example.capstone_be.validation.ValidPassword;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@PasswordMatching(message = "Passwords do not match")
public class UserRegistrationDto {
        @NotNull(message = "Given name cannot be null")
        @NotEmpty(message = "Given name cannot be empty")
        String userName;

        @ValidEmail(message = "Email is not valid")
        @NotNull(message = "Email cannot be null")
        @NotEmpty(message = "Email cannot be empty")
        String userEmail;

        @NotNull(message = "Password cannot be null")
        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @ValidPassword
        String userPassword;

        @NotNull(message = "Matching password cannot be null")
        @NotEmpty(message = "Matching password cannot be empty")
        String matchingPassword;
}
