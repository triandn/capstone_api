package com.example.capstone_be.repository;

import com.example.capstone_be.model.DayBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DayBookRepository extends JpaRepository<DayBook, UUID> {
    @Query(value = "SELECT * FROM daybooks WHERE daybooks.tour_id=:tourId",nativeQuery = true)
    List<DayBook> getDayBookByTourId(Long tourId);
}
