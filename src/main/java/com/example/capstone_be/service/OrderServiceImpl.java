package com.example.capstone_be.service;

import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.dto.wallet.WalletDto;
import com.example.capstone_be.model.Order;
import com.example.capstone_be.model.Wallet;
import com.example.capstone_be.repository.OrderRepository;
import com.example.capstone_be.repository.WalletRepository;
import com.example.capstone_be.util.enums.OrderStatusEnum;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final WalletService walletService;

    private final WalletRepository walletRepository;



    public OrderServiceImpl(OrderRepository orderRepository, WalletService walletService, WalletRepository walletRepository) {
        this.orderRepository = orderRepository;
        this.walletService = walletService;
        this.walletRepository = walletRepository;
    }

    @Transactional
    @Override
    public void requestFromAdmin(String status, UUID orderId) {
        Wallet wallet = walletRepository.getWalletByOrderId(orderId);
        Order order = orderRepository.getOrderByOrderId(orderId);
        BigDecimal totalMoneyUpdate;
        if(status.equals(OrderStatusEnum.SUCCESS.toString())){
            totalMoneyUpdate = wallet.getTotalMoney().subtract(order.getPrice());
            walletRepository.updateTotalMoney(wallet.getWalletId(),totalMoneyUpdate);
            orderRepository.updateStatus(OrderStatusEnum.SUCCESS.toString(),orderId);
        } else if (status.equals(OrderStatusEnum.FAILURE.toString())) {
            totalMoneyUpdate = wallet.getTotalMoney().add(order.getPrice());
            walletRepository.updateTotalMoney(wallet.getWalletId(),totalMoneyUpdate);
            orderRepository.updateStatus(OrderStatusEnum.SUCCESS.toString(),orderId);
        }
    }

    @Override
    public List<OrderDto> getAllOrder() {
        OrderDto orderDto = null;
        List<OrderDto> orderDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        for (Order item: orderList) {
            orderDto = new OrderDto();
            orderDto.setOrderDate(item.getOrderDate());
            orderDto.setStatusOrder(item.getStatusOrder());
            orderDto.setPrice(item.getPrice());
            orderDto.setOrderId(item.getOrderId());
            orderDto.setTimeId(item.getTimeId());
            orderDto.setUserId(item.getUserId());
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }
}
