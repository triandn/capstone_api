package com.example.capstone_be.repository;

import com.example.capstone_be.model.DayBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface DayBookRepository extends JpaRepository<DayBook, UUID> {
    String FIND_DAY_BOOK_BY_TIME_ID="SELECT * FROM daybooks AS dbs INNER JOIN time_book_details AS tbdt ON tbdt.day_book_id = dbs.day_book_id\n" +
            "WHERE tbdt.time_id=:time_id";

    @Query(value = "SELECT * FROM daybooks WHERE date_name BETWEEN :start AND :end AND tour_id=:tour_id",nativeQuery = true)
    Page<DayBook> getDayBookByTourIdPaging(@Param("tour_id") Long tour_id, @Param("start") Date tart,@Param("end") Date end, Pageable pageable);

    @Query(value = "SELECT * FROM daybooks WHERE daybooks.tour_id=:tour_id",nativeQuery = true)
    List<DayBook> getDayBookByTourId(Long tour_id);

    @Query(value = "SELECT * FROM daybooks WHERE daybooks.tour_id=:tour_id",nativeQuery = true)
    Page<DayBook> getDayBookByTourIdPageable(@Param("tour_id") Long tour_id,Pageable pageable);

    @Query(value =FIND_DAY_BOOK_BY_TIME_ID,nativeQuery = true)
    DayBook getDayBookByTimeId(@Param("time_id") UUID time_id);
}
