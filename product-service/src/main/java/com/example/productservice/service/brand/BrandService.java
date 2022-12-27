package com.example.productservice.service.brand;

import com.example.productservice.dto.brand.BrandRequestDto;
import com.example.productservice.dto.brand.BrandResponseDto;

import java.util.List;

public interface BrandService {
    List<BrandResponseDto> brandsList();
    BrandResponseDto brandByName(String name);
    BrandResponseDto addBrand(BrandRequestDto brandRequestDto);
    BrandResponseDto updateBrand(BrandRequestDto brandRequestDto);
    void deleteBrand(Long id);

}
