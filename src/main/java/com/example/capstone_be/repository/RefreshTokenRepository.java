package com.example.capstone_be.repository;

import com.example.capstone_be.model.RefreshToken;
import com.example.capstone_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    @Modifying
    int deleteByUser(User user);

    @Query(value = "SELECT * FROM refresh_tokens WHERE refresh_tokens.user_id=:user_id",nativeQuery = true)
    List<RefreshToken> getRefreshTokenByUserId(UUID user_id);

    @Modifying
    @Query(value = "DELETE FROM refresh_tokens WHERE refresh_tokens.token=:token",nativeQuery = true)
    void deleteByToken(String token);

    @Query(value = "DELETE FROM refresh_tokens WHERE refresh_tokens.user_id=:user_id",nativeQuery = true)
    void deleteByUserId(UUID user_id);
}
