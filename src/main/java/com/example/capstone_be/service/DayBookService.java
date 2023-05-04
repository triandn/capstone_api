package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.model.DayBook;

import java.util.List;
import java.util.UUID;

public interface DayBookService {
    List<DayBookDto> getAllDayBook();

    DayBookDto createDayBooking(DayBookDto dayBookDto);

    void deleteByDayBookId(UUID id);

    DayBookDto updateByDayBookId(DayBookDto dayBookDto, UUID id);

    DayBookDto getDayBookingById(UUID id);
}
