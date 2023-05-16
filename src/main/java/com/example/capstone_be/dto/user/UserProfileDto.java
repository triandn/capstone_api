package com.example.capstone_be.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    private String description;

    private String address;

    private String phoneNumber;

    private String language;

    private String urlImage;
}
