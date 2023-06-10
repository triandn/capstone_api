package com.example.capstone_be.service;

import com.example.capstone_be.controller.AuthController;
import com.example.capstone_be.dto.user.UserForUpdateDto;
import com.example.capstone_be.dto.user.UserPasswordDto;
import com.example.capstone_be.dto.user.UserProfileDto;
import com.example.capstone_be.dto.user.UserRegistrationDto;
import com.example.capstone_be.exception.BadRequestException;
import com.example.capstone_be.exception.ComparisonException;
import com.example.capstone_be.exception.InvalidOldPasswordException;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.*;
import com.example.capstone_be.repository.ConfirmationTokenRepository;
import com.example.capstone_be.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailService emailService;

    private final ModelMapper mapper;

    @Value("${jwt.secret}")
    private String secretKey;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public UserServiceImpl(ModelMapper mapper, ConfirmationTokenRepository confirmationTokenRepository) {
        this.mapper = mapper;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public ResponseEntity<?> saveUser(UserRegistrationDto userRegistrationDto) {
        if (userRepository.existsByUserEmail(userRegistrationDto.getUserEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        userRegistrationDto.setUserPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getUserPassword()));

//        userRepository.save(mapper.map(userRegistrationDto,User.class));

        User user = mapper.map(userRegistrationDto, User.class);

        VerificationToken verificationToken = new VerificationToken(user);

        confirmationTokenRepository.save(verificationToken);

        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userRegistrationDto.getUserEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                +"https://221.132.33.161:9000/api/user/confirm-account?token="+verificationToken.getConfirmationToken());
        emailService.sendEmail(mailMessage);

        System.out.println("Confirmation Token: " + verificationToken.getConfirmationToken());

        return ResponseEntity.ok("Verify email by the link sent on your email address");
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        VerificationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByUserEmailIgnoreCase(token.getUser().getUserEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByUserEmail(email);
    }

    @Override
    public User getUserByUserId(UUID id) {
        return userRepository.getUserByUserId(id);
    }

    @Override
    public User getUserByUserId(String bearerToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(bearerToken).getBody();
            String email = claims.getSubject();
            User user_refresh_token = userRepository.getUserByUserEmail(email);

            UUID user_id = user_refresh_token.getUserId();
            return userRepository.getUserByUserId(user_id);
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: "+e);
        } catch (SignatureException e) {
            Logger.getLogger(AuthController.class.getName());
        } catch(Exception e){
            System.out.println(" Some other exception in JWT parsing ");
        }
        return null;
    }

    @Override
    public UserProfileDto getUserProfile(String bearerToken) {
        UserProfileDto userProfileDto = new UserProfileDto();
        User user = getUserByUserId(bearerToken);
        userProfileDto.setAddress(user.getAddress());
        userProfileDto.setLanguage(user.getLanguage());
        userProfileDto.setDescription(user.getDescription());
        userProfileDto.setPhoneNumber(user.getPhoneNumber());
        userProfileDto.setUrlImage(user.getUrlImage());
        return userProfileDto;
    }

    @Override
    @Transactional
    public UserForUpdateDto updateUserProfile(String bearerToken, UserForUpdateDto userForUpdateDto) {
        User user = getUserByUserId(bearerToken);
        userRepository.updateProfile(
                userForUpdateDto.getUserName(),
                userForUpdateDto.getDescription(),
                userForUpdateDto.getAddress(),
                userForUpdateDto.getPhoneNumber(),
                userForUpdateDto.getLanguage(),
                userForUpdateDto.getUrlImage(),
                user.getUserId()
        );
        return userForUpdateDto;
    }

    @Override
    public User updateUserByField(UUID userId, Map<String, Object> fields) {
        Optional<User> existingUser = userRepository.findById(userId);
        if(existingUser.isPresent()){
            fields.forEach((key,value)->{
                Field field = ReflectionUtils.findField(User.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingUser.get(),value);
            });
            return userRepository.save(existingUser.get());
        }
        return null;
    }

    @Override
    public void changePassword(UserPasswordDto userPasswordDto) {
        String oldPassword = userPasswordDto.getOldPassword();
        User currentUser = userRepository.getUserByUserEmail(userPasswordDto.getEmail());
        try{
            if (bCryptPasswordEncoder.matches(oldPassword, currentUser.getUserPassword())) {
                userRepository.findById(currentUser.getUserId())
                        .map(user -> {
                            if (userPasswordDto.getNewPassword().equals(userPasswordDto.getConfirmPassword())) {
                                user.setUserPassword(bCryptPasswordEncoder.encode(userPasswordDto.getNewPassword()));
                                //send password to mail
                                SimpleMailMessage mailMessage = new SimpleMailMessage();
                                mailMessage.setTo(currentUser.getUserEmail());
                                mailMessage.setSubject("CHANGE PASSWORD SUCCESS!");
                                mailMessage.setText("New Password here : "
                                        +userPasswordDto.getNewPassword());
                                emailService.sendEmail(mailMessage);
                                return userRepository.save(user);
                            } else {
                                throw new ComparisonException("Confirm password doesn't match new password!");
                            }
                        })
                        .orElseThrow(() -> new NotFoundException("User not found!"));
            } else {
                throw new InvalidOldPasswordException("Wrong old password!");
            }
        }
        catch (Exception e){
            System.out.println("Error: "+ e);
        }
    }
}
