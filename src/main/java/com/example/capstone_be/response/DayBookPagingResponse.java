package com.example.capstone_be.response;

import com.example.capstone_be.dto.daybook.DayBookViewDto;
import com.example.capstone_be.dto.tour.TourViewByUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayBookPagingResponse {
    private List<DayBookViewDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
