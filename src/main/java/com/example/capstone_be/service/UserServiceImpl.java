package com.example.capstone_be.service;

import com.example.capstone_be.dto.user.UserRegistrationDto;
import com.example.capstone_be.model.Category;
import com.example.capstone_be.model.User;
import com.example.capstone_be.model.VerificationToken;
import com.example.capstone_be.repository.ConfirmationTokenRepository;
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
                +"http://221.132.33.161:9000/api/user/confirm-account?token="+verificationToken.getConfirmationToken());
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

}
