package com.example.capstone_be.repository;

import com.example.capstone_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserEmailIgnoreCase(String emailId);

    Boolean existsByUserEmail(String user_email);

    @Query(value = "SELECT * FROM users WHERE user_email=:user_email", nativeQuery = true)
    User getUserByUserEmail(String user_email);
}