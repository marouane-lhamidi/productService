package com.example.productservice.controller;

import com.example.productservice.common.utiles.ApisPath;
import com.example.productservice.dto.image.ImageUploadResponse;
import com.example.productservice.dto.product.ProductRequestDto;
import com.example.productservice.dto.product.ProductResponseDto;
import com.example.productservice.service.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController @AllArgsConstructor @CrossOrigin("*")
public class ProductController {
    ProductService service;

    @GetMapping(ApisPath.GET_ALL_PRODUCTS)
    public ResponseEntity<List<ProductResponseDto>> getProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(service.productsList());
    }

    @GetMapping(ApisPath.GET_PRODUCT_BY_NAME)
    public ResponseEntity<ProductResponseDto> getProductByName(@PathVariable(name = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(service.productByName(name));
    }

    @GetMapping(ApisPath.GET_PRODUCT_IMAGES)
    public ResponseEntity<List<ImageUploadResponse>> getProductImages(@PathVariable(name = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(service.productImages(name));
    }

    @GetMapping(path = ApisPath.GET_IMAGE)
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = service.loadImage(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(path = ApisPath.ADD_PRODUCT, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductResponseDto> addProducts(@RequestParam(value = "file") List<MultipartFile> file, ProductRequestDto productRequestDto){
        file.forEach(n -> System.out.println(n.getOriginalFilename()));
        System.out.println(productRequestDto);
        return null;
        //return ResponseEntity.status(HttpStatus.CREATED).body(service.addProduct(productRequestDto, file));
    }

    @PutMapping(ApisPath.UPDATE_PRODUCT)
    public ResponseEntity<ProductResponseDto> updateProducts(@RequestBody ProductRequestDto productRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateProduct(productRequestDto));
    }

    @DeleteMapping(ApisPath.DELETE_PRODUCT)
    public void deleteProducts(@PathVariable(name = "id") Long id){
        service.deleteProduct(id);
    }

}
