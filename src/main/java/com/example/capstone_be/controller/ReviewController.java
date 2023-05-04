package com.example.capstone_be.controller;


import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.review.ReviewDto;
import com.example.capstone_be.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/create")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        reviewService.createReview(reviewDto);
        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ReviewDto> deleteReviewById(@PathVariable UUID id) {
        reviewService.deleteReviewById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
