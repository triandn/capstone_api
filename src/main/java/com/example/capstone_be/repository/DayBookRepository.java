package com.example.capstone_be.repository;

import com.example.capstone_be.model.DayBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DayBookRepository extends JpaRepository<DayBook, UUID> {
    @Query(value = "SELECT * FROM daybooks WHERE daybooks.tour_id=:tour_id",nativeQuery = true)
    Page<DayBook> getDayBookByTourIdPaging(@Param("tour_id") Long tour_id, Pageable pageable);

    @Query(value = "SELECT * FROM daybooks WHERE daybooks.tour_id=:tour_id",nativeQuery = true)
    List<DayBook> getDayBookByTourId(Long tour_id);
}
