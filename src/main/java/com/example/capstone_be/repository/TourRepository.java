package com.example.capstone_be.repository;

import com.example.capstone_be.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    String FIND_TOUR_BY_CATEGORY_NAME = "SELECT * FROM tours AS t \n" +
            "INNER JOIN tour_category as tc  ON t.tour_id = tc.tour_id \n" +
            "INNER JOIN categories AS c ON c.category_id = tc.category_id \n" +
            "WHERE c.category_name=:category_name";
    @Query(value = FIND_TOUR_BY_CATEGORY_NAME,nativeQuery = true)
    Page<Tour> findTourByCategoryName(@Param("category_name") String category_name, Pageable pageable);
}
