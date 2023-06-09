package com.example.capstone_be.repository;

import com.example.capstone_be.model.TimeBookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeBookRepository extends JpaRepository<TimeBookDetail, UUID> {

    String INSERT_VALUE = "INSERT INTO public.time_book_details(start_time, end_time, day_book_id, is_payment)\n" +
            "\tVALUES (:startTime, :endTime, :dayBookId, :isPayment)";
    String DELETE_VALUE = "DELETE FROM time_book_details WHERE time_book_details.time_id IN(SELECT time_id FROM time_book_details INNER JOIN daybooks \n" +
            "ON daybooks.day_book_id = time_book_details.day_book_id INNER JOIN tours \n" +
            "ON tours.tour_id = daybooks.tour_id WHERE tours.tour_id=:tour_id)";
    @Query(value = "SELECT * FROM time_book_details AS tbdt WHERE tbdt.day_book_id=:day_book_id",nativeQuery = true)
    List<TimeBookDetail> getAllListTimeBookByDayBookId(UUID day_book_id);

    @Modifying
    @Query(value = INSERT_VALUE,nativeQuery = true)
    void saveTimeBookDetail(@Param("startTime") LocalTime startTime, @Param("endTime")LocalTime endTime,
                            @Param("dayBookId") UUID dayBookId, @Param("isPayment") Boolean isPayment);


    @Modifying
    @Query(value = DELETE_VALUE, nativeQuery = true)
    void deleteListTimeByTourId(@Param("tour_id") Long tour_id);


}
