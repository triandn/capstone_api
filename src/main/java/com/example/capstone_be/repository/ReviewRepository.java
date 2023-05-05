package com.example.capstone_be.repository;

import com.example.capstone_be.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query(value = "SELECT * FROM reviews AS rv WHERE rv.tour_id=:tourId", nativeQuery = true)
    List<Review> getAllReviewByTourId(Long tourId);
}
