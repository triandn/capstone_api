package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.TimeBookDetailDto;
import com.example.capstone_be.dto.review.ReviewDto;
import com.example.capstone_be.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ReviewService {
    List<ReviewDto> getAllReview();

    ReviewDto createReview(ReviewDto reviewDto);

    void deleteReviewById(UUID id);

    ReviewDto updateReviewDto(ReviewDto reviewDto, UUID id);

    ReviewDto getReviewById(UUID id);

    Double calAvgRatingReviewForTour(Long tourId);

    List<ReviewDto> getListReviewByTourId(Long tourId);
}
