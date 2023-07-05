package com.example.capstone_be.service;

import com.example.capstone_be.dto.order.OrderDetailDto;
import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.model.Order;
import com.example.capstone_be.response.OrderRespone;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {
    void requestFromAdmin(String status,UUID orderId);

    List<OrderDto> getAllOrder();

    void updateOrderByField(UUID id, Map<String, Object> fields);

    String authorizeOrder(String orderIdBlockchain,String publicKey);

    List<OrderDto> getListOrderByTourId(Long tourId);

    OrderRespone getListOrderByOwner(UUID userId, Integer pageNo, Integer pageSize);


    OrderDetailDto getOrderDetail(UUID order_id,UUID user_id);
}
