package com.example.capstone_be.service;

import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.Category;
import com.example.capstone_be.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;

    private final CategoryRepository categoryRepository;

    private List<Category> categoryListExited;

    public CategoryServiceImpl(ModelMapper mapper, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getAll() {
        final List<Category> prizeList = categoryRepository.findAll();
        return prizeList.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        categoryRepository.save(mapper.map(categoryDto, Category.class));
        return categoryDto;
    }

    @Override
    @Transactional
    public CategoryDto findCategoryById(final Long id) {
        final Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        return mapper.map(category,CategoryDto.class);
    }

    @Override
    public void deleteByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDto updateByCategoryId(CategoryDto categoryDto, Long id) {
        final Category updatedCategory = categoryRepository.findById(id)
                .map(category -> {
                    category.setCategoryId(categoryDto.getCategoryId());
                    category.setCategoryName(categoryDto.getCategoryName());
                    return categoryRepository.save(category);
                })
                .orElseGet(() -> {
                    categoryDto.setCategoryId(id);
                    return categoryRepository.save(mapper.map(categoryDto, Category.class));
                });

        return mapper.map(updatedCategory, CategoryDto.class);
    }
}
