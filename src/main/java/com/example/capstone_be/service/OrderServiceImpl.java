package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.TimeBookViewDto;
import com.example.capstone_be.dto.order.OrderDetailDto;
import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.dto.user.UserViewDto;
import com.example.capstone_be.model.*;
import com.example.capstone_be.repository.*;
import com.example.capstone_be.response.OrderRespone;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.util.enums.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final UserRepository userRepository;
    public OrderServiceImpl(OrderRepository orderRepository, WalletService walletService, WalletRepository walletRepository, TourRepository tourRepository, DayBookRepository dayBookRepository, TimeBookRepository timeBookRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.walletService = walletService;
        this.walletRepository = walletRepository;
        this.tourRepository = tourRepository;
        this.dayBookRepository = dayBookRepository;
        this.timeBookRepository = timeBookRepository;
        this.userRepository = userRepository;
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
        User user = null;
        for (Order item: orderList) {
            orderDto = new OrderDto();
            timeBookViewDto = new TimeBookViewDto();
            user = userRepository.getUserByOrderId(item.getOrderId());
            tour = tourRepository.getTourByOrderId(item.getOrderId());
            dayBook = dayBookRepository.getDayBookByTimeId(item.getTimeId());
            timeBookDetail = timeBookRepository.getTimeBookDetailById(item.getTimeId());

            UserViewDto userViewDto = new UserViewDto();
            userViewDto.setUserId(user.getUserId());
            userViewDto.setRole(user.getRole());
            userViewDto.setAddress(user.getAddress());
            userViewDto.setLanguage(user.getLanguage());
            userViewDto.setUserName(user.getUserName());
            userViewDto.setUserEmail(user.getUserEmail());
            userViewDto.setUrlImage(user.getUrlImage());
            userViewDto.setPhoneNumber(user.getPhoneNumber());

            timeBookViewDto.setStart_time(timeBookDetail.getStart_time());
            timeBookViewDto.setEnd_time(timeBookDetail.getEnd_time());
            timeBookViewDto.setIsDeleted(timeBookDetail.getIsDeleted());
            timeBookViewDto.setTimeId(item.getTimeId());

            orderDto.setTourId(tour.getTourId());
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
            orderDto.setUser(userViewDto);
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
    public OrderRespone getListOrderByOwner(UUID userId,Integer pageNo,Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize, Sort.by("created_at").descending());
        Page<Order> orderListOwner = orderRepository.getListOrderByOwner(userId,paging);

        final OrderRespone orderRespone = new OrderRespone();
        OrderDto orderDto = null;
        List<OrderDto> orderDtoList = new ArrayList<>();
        Tour tour = null;
        DayBook dayBook = null;
        TimeBookViewDto timeBookViewDto;
        TimeBookDetail timeBookDetail = null;
        User user = null;
        for (Order item: orderListOwner) {
            orderDto = new OrderDto();
            timeBookViewDto = new TimeBookViewDto();
            user = userRepository.getUserByOrderId(item.getOrderId());
            tour = tourRepository.getTourByOrderId(item.getOrderId());
            dayBook = dayBookRepository.getDayBookByTimeId(item.getTimeId());
            timeBookDetail = timeBookRepository.getTimeBookDetailById(item.getTimeId());

            UserViewDto userViewDto = new UserViewDto();
            userViewDto.setUserId(user.getUserId());
            userViewDto.setRole(user.getRole());
            userViewDto.setAddress(user.getAddress());
            userViewDto.setLanguage(user.getLanguage());
            userViewDto.setUserName(user.getUserName());
            userViewDto.setUserEmail(user.getUserEmail());
            userViewDto.setUrlImage(user.getUrlImage());
            userViewDto.setPhoneNumber(user.getPhoneNumber());

            timeBookViewDto.setStart_time(timeBookDetail.getStart_time());
            timeBookViewDto.setEnd_time(timeBookDetail.getEnd_time());
            timeBookViewDto.setIsDeleted(timeBookDetail.getIsDeleted());
            timeBookViewDto.setTimeId(item.getTimeId());

            orderDto.setTourId(tour.getTourId());
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
            orderDto.setUser(userViewDto);
            orderDtoList.add(orderDto);
        }

        orderRespone.setContent(orderDtoList);
        orderRespone.setPageNo(orderListOwner.getNumber() + 1);
        orderRespone.setPageSize(orderListOwner.getSize());
        orderRespone.setTotalElements(orderListOwner.getTotalElements());
        orderRespone.setTotalPages(orderListOwner.getTotalPages());
        return orderRespone;
    }

    @Override
    public OrderDetailDto getOrderDetail(UUID order_id,UUID user_id) {
        Order order = orderRepository.getOrderDetail(user_id,order_id);
        Tour tour = tourRepository.getTourByOrderId(order_id);
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(order_id);
        orderDetailDto.setOrderDate(order.getCreatedAt());
        orderDetailDto.setStatusOrder(order.getStatusOrder());
        orderDetailDto.setOrderIdBlockChain(order.getOrderIdBlockChain());
        orderDetailDto.setPublicKey(order.getPublicKey());
        orderDetailDto.setPrice(order.getPrice());
        orderDetailDto.setCity(tour.getCity());
        orderDetailDto.setImageMain(tour.getImageMain());
        orderDetailDto.setTour_title(tour.getTitle());
        orderDetailDto.setPriceOnePerson(tour.getPriceOnePerson());
        return orderDetailDto;
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

    @Transactional
    @Override
    public String authorizeOrder(String orderIdBlockchain, String publicKey) {
        Order orderOptional = orderRepository.getOrderByPublicKey(orderIdBlockchain,publicKey);
        if(orderOptional.getPublicKey().equals(publicKey)){
            orderRepository.updateStatus(OrderStatusEnum.USED.toString(),orderOptional.getOrderId());
            return "SUCCESS";
        }
        return "FAIL";
    }

}
