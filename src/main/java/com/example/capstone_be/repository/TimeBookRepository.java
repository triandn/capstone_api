package com.example.capstone_be.repository;

import com.example.capstone_be.model.TimeBookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeBookRepository extends JpaRepository<TimeBookDetail, UUID> {
    @Query(value = "SELECT * FROM time_book_details AS tbdt WHERE tbdt.day_book_id=:day_book_id",nativeQuery = true)
    List<TimeBookDetail> getAllListTimeBookByDayBookId(UUID day_book_id);
}
