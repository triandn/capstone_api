package com.example.capstone_be.dto.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDto {

    @Email
    private String email;

    @NotEmpty
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$",message = "Password must be least 8 character, 1 uppercase letter, 1 lowercase letter, 1 special character, 1 number")
    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String confirmPassword;
}
