package com.example.productservice.service.product.impl;

import com.example.productservice.common.exception.InvalidInputException;
import com.example.productservice.common.utiles.MessagesCode;
import com.example.productservice.dto.image.ImageUploadResponse;
import com.example.productservice.dto.product.ProductRequestDto;
import com.example.productservice.dto.product.ProductResponseDto;
import com.example.productservice.entitie.*;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.repository.*;
import com.example.productservice.service.file.FilesStorageService;
import com.example.productservice.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional @AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    ProductRepository repository;
    TagRepository tagRepository;
    BrandRepository brandRepository;
    CategoryRepository categoryRepository;
    FilesStorageService filesStorageService;
    FileRepository fileRepository;

    @Override
    public List<ProductResponseDto> productsList() {
        List<Product> products= repository.findAll();
        return products.stream().map(ProductMapper::fromProduct).collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto productByName(String name) {
        Product product = repository.findByName(name).orElseThrow(() -> new InvalidInputException(MessagesCode.PRODUCT_NOT_EXIST));
        return ProductMapper.fromProduct(product);
    }

    @Override
    public List<ImageUploadResponse> productImages(String name) {
        Product product = repository.findByName(name).orElseThrow(() -> new InvalidInputException(MessagesCode.PRODUCT_NOT_EXIST));
        List<ImageUploadResponse> responses = new ArrayList<>();
        product.getImages().forEach(file -> responses.add(new ImageUploadResponse(file.getId(), file.getName())));
        return responses;
    }

    @Override
    public Resource loadImage(String filename) {
        Optional<File> file = fileRepository.findByName(filename);
        return file.map(value -> filesStorageService.load(value.getId()))
                .orElseThrow(() -> new InvalidInputException(MessagesCode.FILE_NOT_EXIST));


    }
    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto, List<MultipartFile> files) {
        Optional<Product> productDB = repository.findByName(productRequestDto.getName());
        if (productDB.isPresent())
            throw new InvalidInputException(MessagesCode.PRODUCT_ALREADY_EXIST);

        List<Tag> tags = productRequestDto.getTagNames()
                .stream()
                .map(t-> tagRepository.findByName(t.toLowerCase())
                        .orElseThrow(() -> new InvalidInputException(MessagesCode.TAG_NOT_EXIST)))
                .collect(Collectors.toList());
        Category category = categoryRepository.findByName(productRequestDto.getCategoryName().toLowerCase())
                .orElseThrow(() -> new InvalidInputException(MessagesCode.CATEGORY_NOT_EXIST));
        Brand brand = brandRepository.findByName(productRequestDto.getBrandName().toLowerCase())
                .orElseThrow(() -> new InvalidInputException(MessagesCode.BRAND_NOT_EXIST));
        List<File> file = files.stream().map(filesStorageService::save).collect(Collectors.toList());

        Product product = ProductMapper.fromProductRequestDto(productRequestDto);

        product.setBrand(brand);
        product.setCategory(category);
        product.setTags(tags);
        product.setImages(file);

        return ProductMapper.fromProduct(repository.save(product));
    }

    @Override
    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = repository.findById(id);
        if (product.isPresent()) {
            repository.deleteById(id);
            product.get().getImages().forEach(file -> filesStorageService.delete(file.getId()));
        }else {
            throw new InvalidInputException(MessagesCode.PRODUCT_NOT_EXIST);
        }

    }

}
