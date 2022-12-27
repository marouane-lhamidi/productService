package com.example.productservice.mapper;

import com.example.productservice.dto.image.ImageUploadResponse;
import com.example.productservice.dto.product.ProductRequestDto;
import com.example.productservice.dto.product.ProductResponseDto;
import com.example.productservice.entitie.Product;
import com.example.productservice.entitie.Tag;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductMapper {
    private ProductMapper(){}

    public static Product fromProductRequestDto(ProductRequestDto requestDto) {
        return Product.builder()
                .name(requestDto.getName())
                .price(Double.valueOf(requestDto.getPrice()))
                .quantity(Integer.valueOf(requestDto.getQuantity()))
                .description(requestDto.getDescription())
                .images(new ArrayList<>())
                .tags(new ArrayList<>())
                .build();
    }
    public static ProductResponseDto fromProduct(Product product) {
        return ProductResponseDto.builder()
                .id(String.valueOf(product.getId()))
                .name(product.getName())
                .description(product.getDescription())
                .quantity(String.valueOf(product.getQuantity()))
                .price(String.valueOf(product.getPrice()))
                .brandName(product.getBrand().getName())
                .categoryName(product.getCategory().getName())
                .tagNames(product.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .imagesName(product.getImages().stream().map(file -> new ImageUploadResponse(file.getId(),file.getName())).collect(Collectors.toList()))
                .build();
    }
}
