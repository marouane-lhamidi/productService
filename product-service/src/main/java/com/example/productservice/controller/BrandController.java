package com.example.productservice.controller;

import com.example.productservice.common.utiles.ApisPath;
import com.example.productservice.dto.brand.BrandRequestDto;
import com.example.productservice.dto.brand.BrandResponseDto;
import com.example.productservice.service.brand.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor @CrossOrigin("*")
public class BrandController {
    BrandService service;

    @GetMapping(ApisPath.GET_ALL_BRANDS)
    public ResponseEntity<List<BrandResponseDto>> getBrands(){
        return ResponseEntity.status(HttpStatus.OK).body(service.brandsList());
    }

    @GetMapping(ApisPath.GET_BRAND_BY_NAME)
    public ResponseEntity<BrandResponseDto> getBrandByName(@PathVariable(name = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(service.brandByName(name));
    }

    @PostMapping(ApisPath.ADD_BRAND)
    public ResponseEntity<BrandResponseDto> addBrands(@RequestBody BrandRequestDto brandRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBrand(brandRequestDto));
    }

    @PutMapping(ApisPath.UPDATE_BRAND)
    public ResponseEntity<BrandResponseDto> updateBrands(@RequestBody BrandRequestDto brandRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateBrand(brandRequestDto));
    }

    @DeleteMapping(ApisPath.DELETE_BRAND)
    public void deleteBrands(@PathVariable(name = "id") Long id){
        service.deleteBrand(id);
    }

}
