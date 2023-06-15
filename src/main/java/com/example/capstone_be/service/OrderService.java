package com.example.capstone_be.service;

import com.example.capstone_be.dto.order.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    void requestFromAdmin(String status,UUID orderId);

    List<OrderDto> getAllOrder();
}
