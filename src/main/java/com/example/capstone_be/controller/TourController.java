package com.example.capstone_be.controller;


import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.dto.tour.TourByCategoryDto;
import com.example.capstone_be.dto.tour.TourDetailDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.dto.tour.TourViewDto;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.repository.TourRepository;
import com.example.capstone_be.service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/tour")
public class TourController {
    private final TourService tourService;

    private final TourRepository tourRepository;

    public TourController(TourService tourService, TourRepository tourRepository) {
        this.tourService = tourService;
        this.tourRepository = tourRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<TourDto> createTour(@RequestBody TourDto tourDto) {
        tourService.createTour(tourDto);
        return new ResponseEntity<>(tourDto, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<TourViewDto>> getAllTour() {
        final List<TourViewDto> tourViewDtos = tourService.getAll();
        return new ResponseEntity<>(tourViewDtos, HttpStatus.OK);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<TourByCategoryDto>> getTourByCategoryName(@PathVariable String categoryName) {
        List<TourByCategoryDto> tourByCategoryDto = tourService.getTourByCategoryName(categoryName);
        return new ResponseEntity(tourByCategoryDto, HttpStatus.OK);
    }

    @GetMapping("/tour-detail/{tourId}")
    public ResponseEntity<TourDetailDto> getTourDetailById(@PathVariable Long tourId) {
        TourDetailDto tourDetailDto = tourService.getTourDetail(tourId);
        return new ResponseEntity(tourDetailDto, HttpStatus.OK);
    }
}
