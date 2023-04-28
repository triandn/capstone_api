package com.example.capstone_be.model;


import com.example.capstone_be.util.enums.RoleEnum;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private UUID userId = UUID.randomUUID();

    @Column(unique = true, length = 30, name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "userPassword", nullable = false)
    private String userPassword;

    @Column(name = "role", nullable = false)
    private String role = RoleEnum.USER.toString();

    @Column(name = "is_enabled")
    private boolean isEnabled = true;

}
