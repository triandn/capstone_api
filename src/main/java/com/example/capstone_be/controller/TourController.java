package com.example.capstone_be.controller;


import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.dto.tour.TourByCategoryDto;
import com.example.capstone_be.dto.tour.TourDetailDto;
import com.example.capstone_be.dto.tour.TourDto;
import com.example.capstone_be.dto.tour.TourViewDto;
import com.example.capstone_be.model.Tour;
import com.example.capstone_be.repository.TourRepository;
import com.example.capstone_be.response.TourRespone;
import com.example.capstone_be.response.TourResponseByCategoryName;
import com.example.capstone_be.response.UpdateResponse;
import com.example.capstone_be.service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/create/")
    public ResponseEntity<TourDto> createTour(@RequestBody TourDto tourDto) {
        tourService.createTour(tourDto);
        return new ResponseEntity<>(tourDto, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public TourRespone getAllTour(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(defaultValue = "5") Integer pageSize) {
        final TourRespone tourViewDtos = tourService.getAll(pageNo,pageSize);
        return tourViewDtos;
    }

    @GetMapping("/{categoryName}")
    public TourResponseByCategoryName getTourByCategoryName(@PathVariable String categoryName,
                                                                         @RequestParam(defaultValue = "1") Integer pageNo,
                                                                         @RequestParam(defaultValue = "5") Integer pageSize) {
        TourResponseByCategoryName tourResponseByCategoryName = tourService.getTourByCategoryName(categoryName,pageNo,pageSize);
        return tourResponseByCategoryName;
    }

    @GetMapping("/tour-detail/{tourId}")
    public ResponseEntity<TourDetailDto> getTourDetailById(@PathVariable Long tourId) {
        TourDetailDto tourDetailDto = tourService.getTourDetail(tourId);
        return new ResponseEntity(tourDetailDto, HttpStatus.OK);
    }

    @DeleteMapping("/tour-delete/{id}")
    public ResponseEntity<TourDto> deleteTour(@PathVariable Long id) {
        tourService.deleteByTourId(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/tour-update/{id}")
    public UpdateResponse updateTour(@RequestBody @Valid TourDto tourDto, @PathVariable Long id, final BindingResult bindingResult) {
        UpdateResponse updateResponse = new UpdateResponse();
        if(bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            updateResponse.setMessage(msg);
            updateResponse.setStatus_code(HttpStatus.BAD_REQUEST.toString());
            return updateResponse;
        }
        tourService.updateByTourId(tourDto, id);
        updateResponse.setMessage("Update Success");
        updateResponse.setStatus_code(HttpStatus.OK.toString());
        return updateResponse ;
    }
}
