package com.example.productservice.service.product;

import com.example.productservice.dto.image.ImageUploadResponse;
import com.example.productservice.dto.product.ProductRequestDto;
import com.example.productservice.dto.product.ProductResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> productsList();
    ProductResponseDto productByName(String name);
    List<ImageUploadResponse> productImages(String name);
    ProductResponseDto addProduct(ProductRequestDto productRequestDto, List<MultipartFile> file);
    ProductResponseDto updateProduct(ProductRequestDto productRequestDto);
    void deleteProduct(Long id);

    Resource loadImage(String filename);
}
