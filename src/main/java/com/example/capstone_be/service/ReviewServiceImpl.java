package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.review.ReviewDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.Review;
import com.example.capstone_be.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ModelMapper mapper;

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ModelMapper mapper, ReviewRepository reviewRepository) {
        this.mapper = mapper;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewDto> getAllReview() {
        List<Review> reviewList = reviewRepository.findAll();
        List<ReviewDto> reviewDtoList = new ArrayList<>();

        for (Review review: reviewList) {
            reviewDtoList.add(mapper.map(review,ReviewDto.class));
        }
        return reviewDtoList;
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        reviewRepository.save(mapper.map(reviewDto,Review.class));
        return reviewDto;
    }

    @Override
    public void deleteReviewById(UUID id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewDto updateReviewDto(ReviewDto reviewDto, UUID id) {
        final Review updatedReview = reviewRepository.findById(id)
                .map(review -> {
                    review.setReviewId(reviewDto.getReviewId());
                    review.setComment(reviewDto.getComment());
                    review.setRating(reviewDto.getRating());
                    return reviewRepository.save(review);
                })
                .orElseGet(() -> {
                    reviewDto.setReviewId(id);
                    return reviewRepository.save(mapper.map(reviewDto, Review.class));
                });

        return mapper.map(updatedReview, ReviewDto.class);
    }

    @Override
    public ReviewDto getReviewById(UUID id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
        return mapper.map(review,ReviewDto.class);
    }
}