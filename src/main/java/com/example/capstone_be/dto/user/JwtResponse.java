package com.example.capstone_be.dto.user;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
