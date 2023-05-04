package com.example.capstone_be.repository;

import com.example.capstone_be.model.ImageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<ImageDetail, UUID> {
    @Query(value = "SELECT * FROM image_details AS imgd WHERE imgd.tour_id=:tourId",nativeQuery = true)
    List<ImageDetail> getImageDetailByTourId(Long tourId);
}
