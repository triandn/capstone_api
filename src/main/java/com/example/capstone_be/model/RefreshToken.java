package com.example.capstone_be.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "refresh_tokens")
public class RefreshToken extends BaseEntity {

    @Id
    @Column(name = "refresh_token_id", nullable = false)
    private UUID refreshTokenId = UUID.randomUUID();

    @Column(name = "user_id", nullable = false,insertable = false,updatable = false)
    private UUID userId;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
