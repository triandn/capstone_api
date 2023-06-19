package com.example.capstone_be.service;

import com.example.capstone_be.dto.statistic.StatisticDto;
import com.example.capstone_be.model.Order;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.repository.OrderRepository;
import com.example.capstone_be.repository.TourRepository;
import com.example.capstone_be.util.enums.LabelEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class StatisticServiceImpl implements StatisticService{
    private final OrderRepository orderRepository;

    public StatisticServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public List<StatisticDto> makeStatistic1(UUID userId) {
        List<Order> orderList1 = orderRepository.getOrderByMonth(1,userId);
        List<Order> orderList2 = orderRepository.getOrderByMonth(2,userId);
        List<Order> orderList3 = orderRepository.getOrderByMonth(3,userId);
        List<Order> orderList4 = orderRepository.getOrderByMonth(4,userId);
        List<Order> orderList5 = orderRepository.getOrderByMonth(5,userId);
        List<Order> orderList6 = orderRepository.getOrderByMonth(6,userId);
        List<Order> orderList7 = orderRepository.getOrderByMonth(7,userId);
        List<Order> orderList8 = orderRepository.getOrderByMonth(8,userId);
        List<Order> orderList9 = orderRepository.getOrderByMonth(9,userId);
        List<Order> orderList10 = orderRepository.getOrderByMonth(10,userId);
        List<Order> orderList11= orderRepository.getOrderByMonth(11,userId);
        List<Order> orderList12= orderRepository.getOrderByMonth(12,userId);
        List<StatisticDto> statisticDtos = new ArrayList<>();
        StatisticDto statisticDto1 = new StatisticDto();
        StatisticDto statisticDto2 = new StatisticDto();
        StatisticDto statisticDto3 = new StatisticDto();
        StatisticDto statisticDto4 = new StatisticDto();
        StatisticDto statisticDto5 = new StatisticDto();
        StatisticDto statisticDto6 = new StatisticDto();
        StatisticDto statisticDto7 = new StatisticDto();
        StatisticDto statisticDto8 = new StatisticDto();
        StatisticDto statisticDto9 = new StatisticDto();
        StatisticDto statisticDto10 = new StatisticDto();
        StatisticDto statisticDto11 = new StatisticDto();
        StatisticDto statisticDto12 = new StatisticDto();

        statisticDto1.setTotalOrder(orderList1.size());
        statisticDto1.setLabel(LabelEnum.January.toString());

        statisticDto2.setTotalOrder(orderList2.size());
        statisticDto2.setLabel(LabelEnum.February.toString());

        statisticDto3.setTotalOrder(orderList3.size());
        statisticDto3.setLabel(LabelEnum.March.toString());

        statisticDto4.setTotalOrder(orderList4.size());
        statisticDto4.setLabel(LabelEnum.April.toString());

        statisticDto5.setTotalOrder(orderList5.size());
        statisticDto5.setLabel(LabelEnum.May.toString());

        statisticDto6.setTotalOrder(orderList6.size());
        statisticDto6.setLabel(LabelEnum.June.toString());

        statisticDto7.setTotalOrder(orderList7.size());
        statisticDto7.setLabel(LabelEnum.July.toString());

        statisticDto8.setTotalOrder(orderList8.size());
        statisticDto8.setLabel(LabelEnum.August.toString());

        statisticDto9.setTotalOrder(orderList9.size());
        statisticDto9.setLabel(LabelEnum.September.toString());


        statisticDto10.setTotalOrder(orderList10.size());
        statisticDto10.setLabel(LabelEnum.October.toString());


        statisticDto11.setTotalOrder(orderList11.size());
        statisticDto11.setLabel(LabelEnum.November.toString());

        statisticDto12.setTotalOrder(orderList12.size());
        statisticDto12.setLabel(LabelEnum.December.toString());

        ArrayList<StatisticDto> statisticDtoArrayList = new ArrayList<>();

        statisticDtoArrayList.add(statisticDto1);
        statisticDtoArrayList.add(statisticDto2);
        statisticDtoArrayList.add(statisticDto3);
        statisticDtoArrayList.add(statisticDto4);
        statisticDtoArrayList.add(statisticDto5);
        statisticDtoArrayList.add(statisticDto6);
        statisticDtoArrayList.add(statisticDto7);
        statisticDtoArrayList.add(statisticDto8);
        statisticDtoArrayList.add(statisticDto9);
        statisticDtoArrayList.add(statisticDto10);
        statisticDtoArrayList.add(statisticDto11);
        statisticDtoArrayList.add(statisticDto12);


        statisticDtos.addAll(statisticDtoArrayList);

        System.out.println("statistic list size:" + statisticDtos.size());
        return  statisticDtos;
    }

}
