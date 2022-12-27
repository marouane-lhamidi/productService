package com.example.productservice.service.category.impl;

import com.example.productservice.common.exception.InvalidInputException;
import com.example.productservice.common.utiles.MessagesCode;
import com.example.productservice.common.utiles.Utiles;
import com.example.productservice.dto.category.CategoryRequestDto;
import com.example.productservice.dto.category.CategoryResponseDto;
import com.example.productservice.entitie.Category;
import com.example.productservice.mapper.CategoryMapper;
import com.example.productservice.repository.CategoryRepository;
import com.example.productservice.service.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional @AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository repository;
    @Override
    public List<CategoryResponseDto> categorysList() {
        List<Category> categorys = repository.findAll();
        return categorys.stream().map(CategoryMapper::fromCategory).collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto categoryByName(String name) {
        Category category = repository.findByName(name.toLowerCase()).orElseThrow(() -> new InvalidInputException(MessagesCode.CATEGORY_NOT_EXIST));
        return CategoryMapper.fromCategory(category);
    }

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {
        if(Utiles.isNullOrEmpty(categoryRequestDto.getName()))
            throw new InvalidInputException(MessagesCode.INPUT_NAME_IS_NULL);
        categoryRequestDto.setName(categoryRequestDto.getName().toLowerCase());

        Optional<Category> category = repository.findByName(categoryRequestDto.getName());
        if (category.isPresent())
            throw new InvalidInputException(MessagesCode.CATEGORY_ALREADY_EXIST);

        Category categoryToSave = repository.save(CategoryMapper.fromCategoryRequestDto(categoryRequestDto));
        return CategoryMapper.fromCategory(categoryToSave);
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto categoryRequestDto) {
        if(Utiles.isNullOrEmpty(categoryRequestDto.getName()))
            throw new InvalidInputException(MessagesCode.INPUT_NAME_IS_NULL);
        categoryRequestDto.setName(categoryRequestDto.getName().toLowerCase());

        Optional<Category> category = repository.findByName(categoryRequestDto.getName());
        if (!category.isPresent())
            throw new InvalidInputException(MessagesCode.CATEGORY_NOT_EXIST);
        category.get().setName(categoryRequestDto.getName() == null ? category.get().getName() : categoryRequestDto.getName());
        category.get().setDescription(categoryRequestDto.getDescription() == null ? category.get().getDescription() : categoryRequestDto.getDescription());

        Category categoryToSave = repository.save(category.get());
        return CategoryMapper.fromCategory(categoryToSave);
    }

    @Override
    public void deleteCategory(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        }else {
            throw new InvalidInputException(MessagesCode.CATEGORY_NOT_EXIST);
        }

    }
}
