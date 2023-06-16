package com.example.capstone_be.dto.user;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;

    private String type = "Bearer ";

    private String refreshToken;

    private String username;

    private String role;

    private Boolean isWallet;

    public JwtResponse(String token, String refreshToken, String username, String role,Boolean isWallet) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.role = role;
        this.isWallet = isWallet;
    }
    public JwtResponse(String token) {
        this.token = token;
    }
}
