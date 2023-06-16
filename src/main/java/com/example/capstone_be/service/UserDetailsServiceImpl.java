package com.example.capstone_be.service;

import com.example.capstone_be.dto.user.JwtRequest;
import com.example.capstone_be.dto.user.JwtResponse;
import com.example.capstone_be.model.CustomUserDetails;
import com.example.capstone_be.model.RefreshToken;
import com.example.capstone_be.model.User;
import com.example.capstone_be.repository.UserRepository;
import com.example.capstone_be.security.JwtAuthenticateProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    private JwtAuthenticateProvider jwtAuthenticateProvider;

    public JwtResponse createJwtResponse(JwtRequest jwtRequest) throws Exception {
        String email = jwtRequest.getEmail();
        String password = jwtRequest.getPassword();
        authenticate(email, password);

        final CustomUserDetails customUserDetails = (CustomUserDetails) loadUserByUsername(email);
        String generateToken = jwtAuthenticateProvider.generateToken(customUserDetails);

        User user = userRepository.getUserByUserEmail(email);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());
        return new JwtResponse(generateToken,refreshToken.getToken(),user.getUserName(),user.getRole(),user.getIsWallet());
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from user");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Email %s not found", email));
        }
        return new CustomUserDetails(user);
    }
}
