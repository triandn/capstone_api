package com.example.capstone_be.service;

import com.example.capstone_be.dto.statistic.*;
import com.example.capstone_be.model.Order;

import java.util.List;
import java.util.UUID;

public interface StatisticService {

    List<StatisticDto> makeStatistic1(UUID userId);

    List<StatisticVenueDto> makeStatisVenueByOneDay(UUID user_id, DateStatistic dateStatistic);

    List<StatisticOrderTotal> makeStatisticOrderTotal(UUID user_id,DateStatistic dateStatistic);

    StatisticResponse statisticRespone(UUID userId, DateStatistic dateStatistic, String type);
}
