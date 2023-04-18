package com.example.capstone_be.repository;

import com.example.capstone_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmailIgnoreCase(String emailId);

    Boolean existsByUserEmail(String email);

    @Query(value = "SELECT * FROM users WHERE email=:email", nativeQuery = true)
    User getUserByUserEmail(String email);
}