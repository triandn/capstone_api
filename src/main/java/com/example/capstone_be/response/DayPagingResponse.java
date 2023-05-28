package com.example.capstone_be.response;

import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.DayBookViewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayPagingResponse {
    private List<DayBookDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
