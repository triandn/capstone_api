package com.example.capstone_be.service;

import com.example.capstone_be.model.Order;

import java.util.List;

public interface StatisticService {

    List<Order> getOrderByMonth();
}
