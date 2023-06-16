package com.example.capstone_be.service;

import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.model.Order;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {
    void requestFromAdmin(String status,UUID orderId);

    List<OrderDto> getAllOrder();

    void updateOrderByField(UUID id, Map<String, Object> fields);

    void authorizeOrder(String orderIdBlockchain,String publicKey);
}
