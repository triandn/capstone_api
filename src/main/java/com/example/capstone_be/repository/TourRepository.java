package com.example.capstone_be.repository;

import com.example.capstone_be.model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    String FIND_TOUR_BY_CATEGORY_NAME = "SELECT * FROM tours AS t \n" +
            "INNER JOIN tour_category as tc  ON t.tour_id = tc.tour_id \n" +
            "INNER JOIN categories AS c ON c.category_id = tc.category_id \n" +
            "WHERE c.category_name=:category_name AND t.is_deleted=false";
    String FIND_TOUR_BY_ORDER_ID="SELECT * FROM tours AS t INNER JOIN daybooks AS d ON d.tour_id = t.tour_id \n" +
            "INNER JOIN time_book_details tbdt ON tbdt.day_book_id = d.day_book_id \n" +
            "INNER JOIN orders AS o ON o.time_id = tbdt.time_id \n" +
            "WHERE o.order_id=:order_id";
    @Query(value = FIND_TOUR_BY_CATEGORY_NAME,nativeQuery = true)
    Page<Tour> findTourByCategoryName(@Param("category_name") String category_name, Pageable pageable);

    @Query(value = "SELECT * FROM tours WHERE tours.user_id=:user_id AND tours.is_deleted=false",nativeQuery = true)
    List<Tour> getAllTourByUserId(UUID user_id);

    @Query(value = "SELECT price_one_person FROM tours WHERE tours.tour_id=:tourId AND tours.is_deleted=false",nativeQuery = true)
    Float getPriceOnePersonByTourId(Long tourId);

    @Query(value = "SELECT * FROM tours WHERE user_id=:user_id AND tours.is_deleted=false",nativeQuery = true)
    Page<Tour> getTourByUserId(@Param("user_id") UUID user_id,Pageable paging);

    @Query(value = "SELECT * FROM tours WHERE tours.is_deleted=false",nativeQuery = true)
    Page<Tour> getAllTour(Pageable paging);

    @Query(value = "SELECT * FROM tours WHERE tours.is_deleted=false",nativeQuery = true)
    List<Tour> getAllTourForChatGpt();

    @Query(value = FIND_TOUR_BY_ORDER_ID,nativeQuery = true)
    Tour getTourByOrderId(UUID order_id);

    @Modifying
    @Query(value = "UPDATE tours SET time_book_start=:time_book_start,time_book_end=:time_book_end,time_slot_length=:time_slot_length WHERE tour_id=:tour_id",nativeQuery = true)
    void updateStartTimeAndEndTime(LocalTime time_book_start,LocalTime time_book_end,int time_slot_length,Long tour_id);

    @Query(value = "SELECT * FROM tours WHERE tours.tour_id=:tour_id AND tours.is_deleted=false",nativeQuery = true)
    Tour getTourById(Long tour_id);
}
