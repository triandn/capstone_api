package com.example.capstone_be.controller;


import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        final List<CategoryDto> categoryDtoList = categoryService.getAll();
        return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id,final BindingResult bindingResult) {
//        if(bindingResult.hasErrors()) {
//            String msg = getMessageBindingResult(bindingResult);
//            return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
//        }
        CategoryDto categoryDto = categoryService.findCategoryById(id);
        return new ResponseEntity(categoryDto, HttpStatus.OK);
    }
    @PostMapping("/create/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody final CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id) {
        categoryService.deleteByCategoryId(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid CategoryDto categoryDto, @PathVariable Long id, final BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        CategoryDto updatedCategoryDto = categoryService.updateByCategoryId(categoryDto, id);
        return new ResponseEntity(updatedCategoryDto, HttpStatus.OK);
    }

}
