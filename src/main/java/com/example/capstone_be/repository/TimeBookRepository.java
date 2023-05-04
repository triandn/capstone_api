package com.example.capstone_be.repository;

import com.example.capstone_be.model.TimeBookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TimeBookRepository extends JpaRepository<TimeBookDetail, UUID> {
}
