package com.example.capstone_be.service;

import com.example.capstone_be.controller.AuthController;
import com.example.capstone_be.exception.TokenRefreshException;
import com.example.capstone_be.model.RefreshToken;
import com.example.capstone_be.model.User;
import com.example.capstone_be.repository.RefreshTokenRepository;
import com.example.capstone_be.repository.UserRepository;
import com.example.capstone_be.security.JwtAuthenticateProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class RefreshTokenService {
    @Value("${JWT_REFRESH_EXPIRATION_MS}")
    private Long refreshTokenDurationMs;

    @Value("${jwt.secret}")
    private String secretKey;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final JwtAuthenticateProvider jwtAuthenticateProvider;

    @Autowired
    private UserRepository userRepository;

    public RefreshTokenService(JwtAuthenticateProvider jwtAuthenticateProvider) {
        this.jwtAuthenticateProvider = jwtAuthenticateProvider;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        System.out.println("token time: "+ Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        try{
            if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                refreshTokenRepository.delete(token);
                throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
            }
        }
        catch (ExpiredJwtException e){
            System.out.println("Error: " + e);
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(UUID userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    @Transactional
    public void deleteRefreshToken(String bearerToken){
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(bearerToken).getBody();
            String email = claims.getSubject();
            User user_refresh_token = userRepository.getUserByUserEmail(email);
            //

            UUID user_id = user_refresh_token.getUserId();
            List<RefreshToken> refreshTokenList = refreshTokenRepository.getRefreshTokenByUserId(user_id);
            List<String> refreshTokenDelete = new ArrayList<>();

            for (RefreshToken refreshToken: refreshTokenList) {
                refreshTokenDelete.add(refreshToken.getToken());
            }

            for (String token_delete: refreshTokenDelete) {
                refreshTokenRepository.deleteByToken(token_delete);
            }
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: "+e);
        } catch (SignatureException e) {
            Logger.getLogger(AuthController.class.getName());
        } catch(Exception e){
            System.out.println(" Some other exception in JWT parsing ");
        }
    }
}
