package com.example.productservice.service.brand.impl;

import com.example.productservice.common.exception.InvalidInputException;
import com.example.productservice.common.utiles.MessagesCode;
import com.example.productservice.common.utiles.Utiles;
import com.example.productservice.dto.brand.BrandRequestDto;
import com.example.productservice.dto.brand.BrandResponseDto;
import com.example.productservice.entitie.Brand;
import com.example.productservice.mapper.BrandMapper;
import com.example.productservice.repository.BrandRepository;
import com.example.productservice.service.brand.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional @AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    BrandRepository repository;
    @Override
    public List<BrandResponseDto> brandsList() {
        List<Brand> brands = repository.findAll();
        return brands.stream().map(BrandMapper::fromBrand).collect(Collectors.toList());
    }

    @Override
    public BrandResponseDto brandByName(String name) {
        Brand brand = repository.findByName(name.toLowerCase()).orElseThrow(() -> new InvalidInputException(MessagesCode.BRAND_NOT_EXIST));
        return BrandMapper.fromBrand(brand);
    }

    @Override
    public BrandResponseDto addBrand(BrandRequestDto brandRequestDto) {
        if(Utiles.isNullOrEmpty(brandRequestDto.getName()))
            throw new InvalidInputException(MessagesCode.INPUT_NAME_IS_NULL);
        brandRequestDto.setName(brandRequestDto.getName().toLowerCase());

        Optional<Brand> brand = repository.findByName(brandRequestDto.getName());
        if (brand.isPresent())
            throw new InvalidInputException(MessagesCode.BRAND_ALREADY_EXIST);

        Brand brandToSave = repository.save(BrandMapper.fromBrandRequestDto(brandRequestDto));
        return BrandMapper.fromBrand(brandToSave);
    }

    @Override
    public BrandResponseDto updateBrand(BrandRequestDto brandRequestDto) {
        if(Utiles.isNullOrEmpty(brandRequestDto.getName()))
            throw new InvalidInputException(MessagesCode.INPUT_NAME_IS_NULL);
        brandRequestDto.setName(brandRequestDto.getName().toLowerCase());

        Optional<Brand> brand = repository.findByName(brandRequestDto.getName());
        if (!brand.isPresent())
            throw new InvalidInputException(MessagesCode.BRAND_NOT_EXIST);
        brand.get().setName(brandRequestDto.getName() == null ? brand.get().getName() : brandRequestDto.getName());
        brand.get().setDescription(brandRequestDto.getDescription() == null ? brand.get().getDescription() : brandRequestDto.getDescription());

        Brand brandToSave = repository.save(brand.get());
        return BrandMapper.fromBrand(brandToSave);
    }

    @Override
    public void deleteBrand(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        }else {
            throw new InvalidInputException(MessagesCode.BRAND_NOT_EXIST);
        }

    }
}
