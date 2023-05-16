package com.example.capstone_be.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordResponse {
    private String message;
    private String status_code;
}
