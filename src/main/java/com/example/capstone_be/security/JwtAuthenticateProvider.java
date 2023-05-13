package com.example.capstone_be.security;

import com.example.capstone_be.model.CustomUserDetails;
import com.example.capstone_be.model.RefreshToken;
import com.example.capstone_be.service.RefreshTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticateProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationDateInMs}")
    private int expiresIn;

    @Value("${JWT_REFRESH_EXPIRATION_MS}")
    private int jwtExpirationMs;

    @Value("${jwt.authorities.key}")
    private String AUTHORITIES_KEY;

    @Value("${jwt.username.key}")
    private String USERNAME_KEY;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public JwtAuthenticateProvider() {
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String generateToken(CustomUserDetails userDetails) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String role = userDetails.getAuthorities().toString();
        System.out.println("Role: " + role);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES_KEY, role)
                .claim(USERNAME_KEY, userDetails.getName())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String generateTokenByRefreshToken(String requestRefreshToken) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        refreshTokenService.verifyExpiration(refreshToken.get()); //
        CustomUserDetails customUserDetails = new CustomUserDetails(refreshToken.get().getUser());
        String token = generateToken(customUserDetails);
        return token;
    }
    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username != null &&
                        username.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token)
        );
    }

    public boolean isTokenExpired(String token) {
        Date expireDate=getExpirationDate(token);
        return expireDate.before(new Date());
    }


    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }
}
