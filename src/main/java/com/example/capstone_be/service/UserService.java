package com.example.capstone_be.service;

import com.example.capstone_be.dto.user.UserForUpdateDto;
import com.example.capstone_be.dto.user.UserPasswordDto;
import com.example.capstone_be.dto.user.UserProfileDto;
import com.example.capstone_be.dto.user.UserRegistrationDto;
import com.example.capstone_be.model.User;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {

    ResponseEntity<?> saveUser(UserRegistrationDto userRegistrationDto);

    ResponseEntity<?> confirmEmail(String confirmationToken);

    User getUserByEmail(String email);

    User getUserByUserId(UUID id);

    User getUserByUserId(String bearerToken);

    UserProfileDto getUserProfile(String bearerToken);

    UserForUpdateDto updateUserProfile(String bearerToken, UserForUpdateDto userForUpdateDto);

    void changePassword(UserPasswordDto userPasswordDto);
}
