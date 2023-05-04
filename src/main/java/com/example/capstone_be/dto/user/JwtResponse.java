package com.example.capstone_be.dto.user;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;

    private String type = "Bearer ";

    private String refreshToken;

    private String username;

    private String role;

    public JwtResponse(String token, String refreshToken, String username, String role) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.role = role;
    }
    public JwtResponse(String token) {
        this.token = token;
    }
}
