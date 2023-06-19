package com.example.capstone_be.service;

import com.example.capstone_be.dto.daybook.DayBookDto;
import com.example.capstone_be.dto.review.ReviewDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.DayBook;
import com.example.capstone_be.model.Review;
import com.example.capstone_be.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ModelMapper mapper;

    private final ReviewRepository reviewRepository;

    private static final DecimalFormat decfor = new DecimalFormat("0.00");

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
    public ReviewDto createReview(ReviewDto reviewDto,UUID userId) {
//        reviewRepository.save(mapper.map(reviewDto,Review.class));
//        return reviewDto;
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setTourId(reviewDto.getTourId());
        review.setComment(reviewDto.getComment());
        review.setUserId(userId);

        reviewRepository.save(review);
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

    @Override
    public Double calAvgRatingReviewForTour(Long tourId) {

        List<Review> reviewList = reviewRepository.getAllReviewByTourId(tourId);
        List<Integer> ratingList = new ArrayList<>();
        for (Review review: reviewList) {
            ratingList.add(review.getRating());
        }
        Double avgRatingReview = Double.valueOf(decfor.format(ratingList.stream().mapToDouble(d -> d).average().orElse(0.0)));
        return avgRatingReview;
    }

    @Override
    public List<ReviewDto> getListReviewByTourId(Long tourId) {
        List<Review> reviewListByTourId = reviewRepository.getAllReviewByTourId(tourId);
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review: reviewListByTourId) {
            reviewDtoList.add(mapper.map(review,ReviewDto.class));
        }
        return reviewDtoList;
    }
}
