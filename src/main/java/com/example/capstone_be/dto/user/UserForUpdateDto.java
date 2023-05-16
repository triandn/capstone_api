package com.example.capstone_be.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForUpdateDto {
    private String userName;

    private String description;

    private String address;

    private String phoneNumber;

    private String language;

    private String urlImage;
}
