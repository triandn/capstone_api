package com.example.capstone_be.service;

import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll();
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto findCategoryById(Long id);

    void deleteById(Long id);

    CategoryDto updateById(CategoryDto categoryDto, Long id);
}
