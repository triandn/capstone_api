package com.example.capstone_be.controller;


import com.example.capstone_be.dto.statistic.DateStatistic;
import com.example.capstone_be.dto.statistic.StatisticDto;
import com.example.capstone_be.dto.statistic.StatisticResponse;
import com.example.capstone_be.dto.statistic.StatisticVenueDto;
import com.example.capstone_be.model.User;
import com.example.capstone_be.service.StatisticService;
import com.example.capstone_be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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

    @PostMapping("/venue/day/{type}")
    public ResponseEntity<?> makeStatisticVenueDay(HttpServletRequest request,
                                                   @RequestBody DateStatistic dateStatistic,
                                                    @PathVariable String type) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        User user = userService.getUserByUserId(bearerToken);
        UUID userId = user.getUserId();
//        StatisticResponse statisticResponse =  statisticService.makeStatisVenueByOneDay(userId,dateStatistic);
        StatisticResponse statisticResponse = statisticService.statisticRespone(userId,dateStatistic,type);
        return new ResponseEntity<>(statisticResponse, HttpStatus.OK);
    }
}
