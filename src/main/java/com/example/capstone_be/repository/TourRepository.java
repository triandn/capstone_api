package com.example.capstone_be.repository;

import com.example.capstone_be.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    String FIND_TOUR_BY_CATEGORY_NAME = "SELECT * FROM tours AS t \n" +
            "INNER JOIN tour_category as tc  ON t.tour_id = tc.tour_id \n" +
            "INNER JOIN categories AS c ON c.category_id = tc.category_id \n" +
            "WHERE c.category_name=:category_name";
    @Query(value = FIND_TOUR_BY_CATEGORY_NAME,nativeQuery = true)
    Page<Tour> findTourByCategoryName(@Param("category_name") String category_name, Pageable pageable);

    @Query(value = "SELECT * FROM tours WHERE tours.user_id=:user_id",nativeQuery = true)
    List<Tour> getAllTourByUserId(UUID user_id);

    @Query(value = "SELECT price_one_person FROM tours WHERE tours.tour_id=:tourId",nativeQuery = true)
    Float getPriceOnePersonByTourId(Long tourId);

    @Query(value = "SELECT * FROM tours WHERE user_id=:user_id",nativeQuery = true)
    Page<Tour> getTourByUserId(@Param("user_id") UUID user_id,Pageable paging);
}
