package com.example.capstone_be.service;

import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.dto.tour.TourByCategoryDto;
import com.example.capstone_be.dto.tour.TourDetailDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.dto.tour.TourViewDto;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.response.TourResponseByCategoryName;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TourService {

    TourDto createTour(TourDto tourDto);

    TourRespone getAll(Integer pageNo, Integer pageSize);

    TourResponseByCategoryName getTourByCategoryName(String categoryName, Integer pageNo, Integer pageSize);

    TourDetailDto getTourDetail(Long tourId);

    void deleteById(Long id);

    TourDto updateById(TourDto tourDto, Long id);
}
