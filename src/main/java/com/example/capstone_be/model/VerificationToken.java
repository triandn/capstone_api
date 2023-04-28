package com.example.capstone_be.model;


import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="verification_tokens")
public class VerificationToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private Long tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public VerificationToken(User user) {
        this.user = user;
        confirmationToken = UUID.randomUUID().toString();
    }
}
