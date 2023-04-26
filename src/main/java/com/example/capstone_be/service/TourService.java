package com.example.capstone_be.service;

import com.example.capstone_be.dto.tour.TourByCategoryDto;
import com.example.capstone_be.dto.tour.TourDetailDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.dto.tour.TourViewDto;

import java.util.List;

public interface TourService {

    TourDto createTour(TourDto tourDto);

    List<TourViewDto> getAll();

    List<TourByCategoryDto> getTourByCategoryName(String categoryName);

    TourDetailDto getTourDetail(Long tourId);
}
