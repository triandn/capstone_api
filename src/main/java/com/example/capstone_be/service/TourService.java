package com.example.capstone_be.service;

import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.dto.tour.*;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.response.TourResponseByCategoryName;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TourService {

    TourCreateDto createTour(TourCreateDto tourDto, UUID userId);

    TourRespone getAll(Integer pageNo, Integer pageSize);

    TourResponseByCategoryName getTourByCategoryName(String categoryName, Integer pageNo, Integer pageSize);

    TourDetailDto getTourDetail(Long tourId);

    ResponseEntity<?> deleteByTourId(Long id);

    TourDto updateByTourId(TourDto tourDto, Long id);
}
