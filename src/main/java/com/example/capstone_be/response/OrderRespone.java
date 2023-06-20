package com.example.capstone_be.response;

import com.example.capstone_be.dto.order.OrderDto;
import com.example.capstone_be.dto.tour.TourViewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRespone {
    private List<OrderDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
