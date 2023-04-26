package com.example.capstone_be.repository;

import com.example.capstone_be.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByConfirmationToken(String confirmationToken);
}
