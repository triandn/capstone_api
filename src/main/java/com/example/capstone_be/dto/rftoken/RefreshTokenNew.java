package com.example.capstone_be.dto.rftoken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenNew {
    private String accessToken;
    private String rfTokenNew;
}
