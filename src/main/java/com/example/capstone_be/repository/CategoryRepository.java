package com.example.capstone_be.repository;

import com.example.capstone_be.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
