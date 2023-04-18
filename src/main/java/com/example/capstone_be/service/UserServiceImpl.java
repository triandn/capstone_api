package com.example.capstone_be.service;

import com.example.capstone_be.dto.user.UserRegistrationDto;
import com.example.capstone_be.model.User;
import com.example.capstone_be.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailService emailService;

    private final ModelMapper mapper;

    public UserServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<?> saveUser(UserRegistrationDto userRegistrationDto) {
        if (userRepository.existsByUserEmail(userRegistrationDto.getUserEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        userRegistrationDto.setUserPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getUserPassword()));

        userRepository.save(mapper.map(userRegistrationDto,User.class));
//
//        ConfirmationToken confirmationToken = new ConfirmationToken();
//
//        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userRegistrationDto.getUserEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : LINK");
        emailService.sendEmail(mailMessage);

//        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

        return ResponseEntity.ok("Verify email by the link sent on your email address");
    }

}
