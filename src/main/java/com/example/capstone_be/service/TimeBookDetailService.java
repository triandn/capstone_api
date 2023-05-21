package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.TimeBookDetailDto;
import com.example.capstone_be.dto.daybook.TimeBookViewDto;
import com.example.capstone_be.model.TimeBookDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TimeBookDetailService {
    List<TimeBookViewDto> getAllTimeBookDetail();

    TimeBookDetailDto createTimeBookDetail(TimeBookDetailDto timeBookDetailDto);

    ResponseEntity<?> deleteByTimeBookId(UUID id);

    TimeBookDetailDto updateByTimeBookId(TimeBookDetailDto timeBookDetailDto, UUID id);

    TimeBookViewDto getTimeBookingById(UUID id);

    List<TimeBookDetailDto> createListTimeBookDetail(List<TimeBookDetailDto> timeBookDetailDtoList);

    List<TimeBookViewDto> getAllTimeBookForDayByDayBookId(UUID dayBookId);

    TimeBookDetail findTimeBookById(UUID id);
}
