package com.example.productservice.mapper;

import com.example.productservice.dto.brand.BrandRequestDto;
import com.example.productservice.dto.brand.BrandResponseDto;
import com.example.productservice.entitie.Brand;

public class BrandMapper {
    private BrandMapper(){}

    public static Brand fromBrandRequestDto(BrandRequestDto requestDto){
        return Brand.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .build();
    }

    public static BrandResponseDto fromBrand(Brand requestDto){
        return BrandResponseDto.builder()
                .id(String.valueOf(requestDto.getId()))
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .build();
    }
}
