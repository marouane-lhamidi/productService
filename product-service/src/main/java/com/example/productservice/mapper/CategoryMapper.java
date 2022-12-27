package com.example.productservice.mapper;

import com.example.productservice.dto.category.CategoryRequestDto;
import com.example.productservice.dto.category.CategoryResponseDto;
import com.example.productservice.entitie.Category;

public class CategoryMapper {
    private CategoryMapper(){}

    public static Category fromCategoryRequestDto(CategoryRequestDto requestDto){
        return Category.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .build();
    }

    public static CategoryResponseDto fromCategory(Category requestDto){
        return CategoryResponseDto.builder()
                .id(String.valueOf(requestDto.getId()))
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .build();
    }
}
