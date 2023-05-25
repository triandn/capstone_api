package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DateBookCreateDto;
import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.DayBookViewDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.model.DayBook;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DayBookService {
    List<DayBookDto> getAllDayBook();

    DateBookCreateDto createDayBooking(DateBookCreateDto dayBookDto);

    ResponseEntity<?> deleteByDayBookId(UUID id);

    DayBookDto updateByDayBookId(DayBookDto dayBookDto, UUID id);

    DayBookViewDto getDayBookingById(UUID id);
    List<DayBookViewDto>getDayAndTimeByTourId(Long tourId);
}
