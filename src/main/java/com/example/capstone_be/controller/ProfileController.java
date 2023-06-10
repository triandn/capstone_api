package com.example.capstone_be.controller;

import com.example.capstone_be.dto.review.ReviewDto;
import com.example.capstone_be.dto.user.UserForUpdateDto;
import com.example.capstone_be.dto.user.UserProfileDto;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.model.User;
import com.example.capstone_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/user-profile/")
    public ResponseEntity<UserProfileDto> getUserProfile(HttpServletRequest request) throws Exception {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        System.out.println("BearToken: " + bearerToken);
        UserProfileDto userProfileDto = userService.getUserProfile(bearerToken);
        return new ResponseEntity<>(userProfileDto,HttpStatus.OK);
    }
//
    @PatchMapping("/user-profile-update/")
    public ResponseEntity<?> updateUserProfileByField(@RequestBody Map<String,Object> fields,HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        User user = userService.getUserByUserId(bearerToken);
        UUID userId = user.getUserId();
        userService.updateUserByField(userId,fields);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

}
