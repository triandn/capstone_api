package com.example.capstone_be.controller;


import com.example.capstone_be.dto.statistic.StatisticDto;
import com.example.capstone_be.model.User;
import com.example.capstone_be.service.StatisticService;
import com.example.capstone_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private UserService userService;

    @Autowired
    private StatisticService statisticService;

    @GetMapping("/make/")
    public ResponseEntity<?> makeStatistic(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        User user = userService.getUserByUserId(bearerToken);
        UUID userId = user.getUserId();
        List<StatisticDto> statisticDtoList =  statisticService.makeStatistic1(userId);
        return new ResponseEntity<>(statisticDtoList, HttpStatus.OK);
    }
}
