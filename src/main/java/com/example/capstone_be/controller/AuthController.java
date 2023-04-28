package com.example.capstone_be.controller;

import com.example.capstone_be.dto.user.JwtRequest;
import com.example.capstone_be.dto.user.TokenRefreshRequest;
import com.example.capstone_be.dto.user.TokenRefreshResponse;
import com.example.capstone_be.exception.TokenRefreshException;
import com.example.capstone_be.model.CustomUserDetails;
import com.example.capstone_be.model.RefreshToken;
import com.example.capstone_be.security.JwtAuthenticateProvider;
import com.example.capstone_be.service.RefreshTokenService;
import com.example.capstone_be.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtAuthenticateProvider jwtAuthenticateProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final JwtRequest loginRequest) throws Exception {
        return ResponseEntity.ok(userDetailsService.createJwtResponse(loginRequest));
    }

    @PostMapping("/refreshtoken")
    public TokenRefreshResponse refreshToken(@Valid @RequestBody TokenRefreshRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String requestRefreshToken = request.getRefreshToken();
        String token = jwtAuthenticateProvider.generateTokenByRefreshToken(requestRefreshToken);
        TokenRefreshResponse tokenRefreshResponse = new TokenRefreshResponse();
        tokenRefreshResponse.setAccessToken(token);
        tokenRefreshResponse.setTokenType("Bearer ");
        tokenRefreshResponse.setRefreshToken(requestRefreshToken);
        return tokenRefreshResponse;
    }
}
