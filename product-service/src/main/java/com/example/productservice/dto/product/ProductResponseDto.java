package com.example.productservice.dto.product;

import com.example.productservice.dto.image.ImageUploadResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class ProductResponseDto {
    private String id;
    private String name;
    private String price;
    private String quantity;
    private String description;
    private String categoryName;
    private String brandName;
    private List<String> tagNames = new ArrayList<>();
    private List<ImageUploadResponse> imagesName = new ArrayList<>();

}
