package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.TimeBookViewDto;
import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.model.*;
import com.example.capstone_be.repository.*;
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

    private final DayBookRepository dayBookRepository;

    private final TimeBookRepository timeBookRepository;
    public OrderServiceImpl(OrderRepository orderRepository, WalletService walletService, WalletRepository walletRepository, TourRepository tourRepository, DayBookRepository dayBookRepository, TimeBookRepository timeBookRepository) {
        this.orderRepository = orderRepository;
        this.walletService = walletService;
        this.walletRepository = walletRepository;
        this.tourRepository = tourRepository;
        this.dayBookRepository = dayBookRepository;
        this.timeBookRepository = timeBookRepository;
    }

    @Transactional
    @Override
    public void requestFromAdmin(String status, UUID orderId) {
        Wallet wallet = walletRepository.getWalletByOrderId(orderId);
        Order order = orderRepository.getOrderByOrderId(orderId);
        BigDecimal totalMoneyUpdate;
        if(status.equals(OrderStatusEnum.SUCCESS.toString())){
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
        DayBook dayBook = null;
        TimeBookViewDto timeBookViewDto;
        TimeBookDetail timeBookDetail = null;
        for (Order item: orderList) {
            orderDto = new OrderDto();
            timeBookViewDto = new TimeBookViewDto();
            tour = tourRepository.getTourByOrderId(item.getOrderId());
            dayBook = dayBookRepository.getDayBookByTimeId(item.getTimeId());
            timeBookDetail = timeBookRepository.getTimeBookDetailById(item.getTimeId());
            timeBookViewDto.setStart_time(timeBookDetail.getStart_time());
            timeBookViewDto.setEnd_time(timeBookDetail.getEnd_time());
            timeBookViewDto.setIsDeleted(timeBookDetail.getIsDeleted());
            timeBookViewDto.setTimeId(item.getTimeId());

            orderDto.setOrderDate(item.getCreatedAt());
            orderDto.setStatusOrder(item.getStatusOrder());
            orderDto.setPrice(item.getPrice());
            orderDto.setOrderIdBlockChain(item.getOrderIdBlockChain());
            orderDto.setPublicKey(item.getPublicKey());
            orderDto.setOrderId(item.getOrderId());
            orderDto.setTimeId(item.getTimeId());
            orderDto.setUserId(item.getUserId());
            orderDto.setCity(tour.getCity());
            orderDto.setDate_name(dayBook.getDate_name());
            orderDto.setImageMain(tour.getImageMain());
            orderDto.setTour_title(tour.getTitle());
            orderDto.setPriceOnePerson(tour.getPriceOnePerson());
            orderDto.setTimeBookViewDto(timeBookViewDto);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    @Override
    public List<OrderDto> getListOrderByTourId(Long tourId) {
        OrderDto orderDto = null;
        List<OrderDto> orderDtoList = new ArrayList<>();
        List<Order> orderList = orderRepository.getListOrderByTourId(tourId);
        Tour tour = tourRepository.getTourById(tourId);
        for (Order item: orderList) {
            orderDto = new OrderDto();
            orderDto.setOrderDate(item.getCreatedAt());
            orderDto.setStatusOrder(item.getStatusOrder());
            orderDto.setPrice(item.getPrice());
            orderDto.setOrderId(item.getOrderId());
            orderDto.setTimeId(item.getTimeId());
            orderDto.setUserId(item.getUserId());
            orderDto.setCity(tour.getCity());
            orderDto.setImageMain(tour.getImageMain());
            orderDto.setTour_title(tour.getTitle());
            orderDto.setOrderIdBlockChain(item.getOrderIdBlockChain());
            orderDto.setPublicKey(item.getPublicKey());
            orderDto.setPriceOnePerson(tour.getPriceOnePerson());
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

    @Override
    public void updateOrderByField(UUID id, Map<String, Object> fields) {
        try
        {
            Optional<Order> existingOrder = orderRepository.findById(id);
            if(existingOrder.isPresent()){
                fields.forEach((key,value)->{
                    Field field = ReflectionUtils.findField(Order.class,key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingOrder.get(),value);
                });
                orderRepository.save(existingOrder.get());
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception:" + e);
        }

    }

    @Override
    public void authorizeOrder(String orderIdBlockchain, String publicKey) {
        Order orderOptional = orderRepository.getOrderByPublicKey(orderIdBlockchain,publicKey);
        if(orderOptional.getPublicKey().equals(publicKey)){
            orderRepository.updateStatus(OrderStatusEnum.USED.toString(),orderOptional.getOrderId());
        }
    }
}
