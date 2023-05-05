package com.example.capstone_be.controller;


import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.review.ReviewDto;
import com.example.capstone_be.model.Review;
import com.example.capstone_be.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/create/")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        reviewService.createReview(reviewDto);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReviewDto> deleteReviewById(@PathVariable UUID id) {
        reviewService.deleteReviewById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@RequestBody @Valid ReviewDto reviewDto, @PathVariable UUID id, final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        ReviewDto updatedReviewDto = reviewService.updateReviewDto(reviewDto, id);
        return new ResponseEntity(updatedReviewDto, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<ReviewDto>> getAllReview() {
        final List<ReviewDto> reviewDtoList = reviewService.getAllReview();
        return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
    }
    @GetMapping("/review-tour/{tour_id}")
    public ResponseEntity<List<ReviewDto>> getAllReviewByTourId(@PathVariable Long tour_id) {
        final List<ReviewDto> reviewDtoList = reviewService.getListReviewByTourId(tour_id);
        return new ResponseEntity<>(reviewDtoList, HttpStatus.OK);
    }

}
