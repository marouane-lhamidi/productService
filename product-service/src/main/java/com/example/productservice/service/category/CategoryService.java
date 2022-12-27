package com.example.productservice.service.category;

import com.example.productservice.dto.category.CategoryRequestDto;
import com.example.productservice.dto.category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> categorysList();
    CategoryResponseDto categoryByName(String name);
    CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto);
    void deleteCategory(Long id);

}
