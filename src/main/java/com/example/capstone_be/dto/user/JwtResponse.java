package com.example.capstone_be.dto.user;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;

    private String type = "Bearer";

    private String refreshToken;

    public JwtResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
