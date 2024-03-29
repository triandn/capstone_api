package com.example.capstone_be.controller;


import com.example.capstone_be.dto.user.UserPasswordDto;
import com.example.capstone_be.dto.user.UserRegistrationDto;
import com.example.capstone_be.response.ChangePasswordResponse;
import com.example.capstone_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto,final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<?> newUserDto = userService.saveUser(userRegistrationDto);
        return new ResponseEntity(newUserDto, HttpStatus.CREATED);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return userService.confirmEmail(confirmationToken);
    }

    @PutMapping("/change-password/")
    public ResponseEntity<?> changePassword(@Valid @RequestBody final UserPasswordDto userPasswordDto,
                                            final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(userPasswordDto);
        ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse();
        changePasswordResponse.setMessage("Change Password Success");
        changePasswordResponse.setStatus_code(HttpStatus.OK.toString());
        return new ResponseEntity<>(changePasswordResponse, HttpStatus.OK);
    }
}
