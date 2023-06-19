package com.example.capstone_be.controller;


import com.example.capstone_be.dto.review.ReviewDto;
import com.example.capstone_be.model.User;
import com.example.capstone_be.repository.UserRepository;
import com.example.capstone_be.service.ReviewService;
import com.example.capstone_be.util.common.CommonFunction;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    private final UserRepository userRepository;

    public ReviewController(ReviewService reviewService, UserRepository userRepository) {
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }
    @PostMapping("/create/")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto, HttpServletRequest request) {
        String bearerToken = CommonFunction.getBearToken(request);
        Claims claims = CommonFunction.getClaims(bearerToken);
        String email = claims.getSubject();
        User user = userRepository.getUserByUserEmail(email);
        reviewService.createReview(reviewDto,user.getUserId());
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
