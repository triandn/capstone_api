package com.example.capstone_be.service;

import com.example.capstone_be.dto.statistic.StatisticDto;
import com.example.capstone_be.model.Order;

import java.util.List;
import java.util.UUID;

public interface StatisticService {


    List<StatisticDto> makeStatistic1(UUID userId);
}
