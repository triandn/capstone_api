package com.example.capstone_be.service;

import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.model.Order;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.model.Wallet;
import com.example.capstone_be.repository.OrderRepository;
import com.example.capstone_be.repository.TourRepository;
import com.example.capstone_be.repository.WalletRepository;
import com.example.capstone_be.util.enums.OrderStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WalletService walletService;
    private final WalletRepository walletRepository;

    private final TourRepository tourRepository;

    public OrderServiceImpl(OrderRepository orderRepository, WalletService walletService, WalletRepository walletRepository, TourRepository tourRepository) {
        this.orderRepository = orderRepository;
        this.walletService = walletService;
        this.walletRepository = walletRepository;
        this.tourRepository = tourRepository;
    }

    @Transactional
    @Override
    public void requestFromAdmin(String status, UUID orderId) {
        Wallet wallet = walletRepository.getWalletByOrderId(orderId);
        Order order = orderRepository.getOrderByOrderId(orderId);
        BigDecimal totalMoneyUpdate;
        if(status.equals(OrderStatusEnum.SUCCESS.toString())){
//            totalMoneyUpdate = wallet.getTotalMoney().subtract(order.getPrice());
//            walletRepository.updateTotalMoney(wallet.getWalletId(),totalMoneyUpdate);
            orderRepository.updateStatus(OrderStatusEnum.SUCCESS.toString(),orderId);
        } else if (status.equals(OrderStatusEnum.CANCEL.toString())) {
            totalMoneyUpdate = wallet.getTotalMoney().add(order.getPrice());
            walletRepository.updateTotalMoney(wallet.getWalletId(),totalMoneyUpdate);
            orderRepository.updateStatus(OrderStatusEnum.CANCEL.toString(),orderId);
        }
    }

    @Override
    public List<OrderDto> getAllOrder() {
        OrderDto orderDto = null;
        List<OrderDto> orderDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        Tour tour = null;
        for (Order item: orderList) {
            orderDto = new OrderDto();
            tour = tourRepository.getTourByOrderId(item.getOrderId());
            orderDto.setOrderDate(item.getOrderDate());
            orderDto.setStatusOrder(item.getStatusOrder());
            orderDto.setPrice(item.getPrice());
            orderDto.setOrderId(item.getOrderId());
            orderDto.setTimeId(item.getTimeId());
            orderDto.setUserId(item.getUserId());
            orderDto.setCity(tour.getCity());
            orderDto.setImageMain(tour.getImageMain());
            orderDto.setTour_title(tour.getTitle());
            orderDto.setPriceOnePerson(tour.getPriceOnePerson());
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    @Override
    public Order updateOrderByField(UUID id, Map<String, Object> fields) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if(existingOrder.isPresent()){
            fields.forEach((key,value)->{
                Field field = ReflectionUtils.findField(Order.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingOrder.get(),value);
            });
            return orderRepository.save(existingOrder.get());
        }
        return null;
    }

    @Override
    public void authorizeOrder(String orderIdBlockchain, String publicKey) {
        Order orderOptional = orderRepository.getOrderByPublicKey(orderIdBlockchain,publicKey);
        if(orderOptional.getPublicKey().equals(publicKey)){
            orderRepository.updateStatus(OrderStatusEnum.USED.toString(),orderOptional.getOrderId());
        }
    }
}
