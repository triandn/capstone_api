package com.example.capstone_be.controller;

import com.example.capstone_be.dto.user.JwtRequest;
import com.example.capstone_be.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final JwtRequest loginRequest) throws Exception{
        return ResponseEntity.ok(userDetailsService.createJwtResponse(loginRequest));
    }
}
