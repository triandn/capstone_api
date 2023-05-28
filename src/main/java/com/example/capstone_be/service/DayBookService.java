package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DateBookCreateDto;
import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.daybook.DayBookViewDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.response.DayBookPagingResponse;
import com.example.capstone_be.response.DayPagingResponse;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.*;

public interface DayBookService {
    List<DayBookDto> getAllDayBook();
    DateBookCreateDto createDayBooking(DateBookCreateDto dayBookDto);
    ResponseEntity<?> deleteByDayBookId(UUID id);
    DayBookViewDto getDayBookingById(UUID id);
    DayBookPagingResponse getDayAndTimeByTourId(Long tourId, String start, String end, Integer pageNo, Integer pageSize)throws ParseException;;
    List<DayBookDto> getDayBookByTourId(Long tourId);
    DayPagingResponse getDayBookByTourIdPaging(Long tourId,String start,String end,Integer pageNo, Integer pageSize) throws ParseException;

    void updateDayByField(List<Map<String, Object>> fields);
}
