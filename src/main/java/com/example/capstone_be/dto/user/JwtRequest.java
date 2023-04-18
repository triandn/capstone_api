package com.example.capstone_be.dto.user;

import lombok.Data;

@Data
public class JwtRequest {
    private String email;
    private String password;
}
