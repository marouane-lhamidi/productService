package com.example.productservice.controller;

import com.example.productservice.common.utiles.ApisPath;
import com.example.productservice.dto.category.CategoryRequestDto;
import com.example.productservice.dto.category.CategoryResponseDto;
import com.example.productservice.service.category.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor @CrossOrigin("*")
public class CategoryController {
    CategoryService service;

    @GetMapping(ApisPath.GET_ALL_CATEGORIES)
    public ResponseEntity<List<CategoryResponseDto>> getCategorys(){
        return ResponseEntity.status(HttpStatus.OK).body(service.categorysList());
    }

    @GetMapping(ApisPath.GET_CATEGORY_BY_NAME)
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@PathVariable(name = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(service.categoryByName(name));
    }

    @PostMapping(ApisPath.ADD_CATEGORY)
    public ResponseEntity<CategoryResponseDto> addCategorys(@RequestBody CategoryRequestDto categoryRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCategory(categoryRequestDto));
    }

    @PutMapping(ApisPath.UPDATE_CATEGORY)
    public ResponseEntity<CategoryResponseDto> updateCategorys(@RequestBody CategoryRequestDto categoryRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateCategory(categoryRequestDto));
    }

    @DeleteMapping(ApisPath.DELETE_CATEGORY)
    public void deleteCategorys(@PathVariable(name = "id") Long id){
        service.deleteCategory(id);
    }

}
