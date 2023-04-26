package com.example.capstone_be.service;

import com.example.capstone_be.dto.user.UserRegistrationDto;
import com.example.capstone_be.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> saveUser(UserRegistrationDto userRegistrationDto);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}
