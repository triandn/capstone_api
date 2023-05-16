package com.example.capstone_be.repository;

import com.example.capstone_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserEmailIgnoreCase(String emailId);

    Boolean existsByUserEmail(String user_email);

    @Query(value = "SELECT * FROM users WHERE user_email=:user_email", nativeQuery = true)
    User getUserByUserEmail(String user_email);

    @Query(value = "SELECT * FROM users WHERE user_id=:user_id", nativeQuery = true)
    User getUserByUserId(UUID user_id);

    @Modifying
    @Query(value = "UPDATE users SET role=:role WHERE users.user_id =:user_id",nativeQuery = true)
    void updateRole(UUID user_id, String role);

    @Query(value = "SELECT user_password FROM users WHERE user_email=:user_email", nativeQuery = true)
    String getPasswordByEmail(String user_email);

    @Modifying
    @Query(value = "UPDATE users SET user_name=:user_name, description=:description,address=:address,phone_number=:phone_number,language=:language,url_image=:url_image WHERE users.user_id=:user_id",nativeQuery = true)
    void updateProfile(String user_name,String description, String address,String phone_number,String language,String url_image,UUID user_id);
}