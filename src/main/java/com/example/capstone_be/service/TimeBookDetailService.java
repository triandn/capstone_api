package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.TimeBookDetailDto;
import com.example.capstone_be.model.TimeBookDetail;

import java.util.List;
import java.util.UUID;

public interface TimeBookDetailService {
    List<TimeBookDetailDto> getAllTimeBookDetail();

    TimeBookDetailDto createTimeBookDetail(TimeBookDetailDto timeBookDetailDto);

    void deleteByTimeBookId(UUID id);

    TimeBookDetailDto updateByTimeBookId(TimeBookDetailDto timeBookDetailDto, UUID id);

    TimeBookDetailDto getTimeBookingById(UUID id);

    List<TimeBookDetailDto> createListTimeBookDetail(List<TimeBookDetailDto> timeBookDetailDtoList);
}
