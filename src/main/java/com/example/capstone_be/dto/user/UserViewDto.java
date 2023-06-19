package com.example.capstone_be.dto.user;

import com.example.capstone_be.util.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewDto implements Serializable {
    private UUID userId = UUID.randomUUID();

    private String userName;

    private String userEmail;

    private String description;

    private String address;

    private String phoneNumber;

    private String language;

    private String urlImage;

    private String role = RoleEnum.USER.toString();

    private boolean isEnabled = true;
}
