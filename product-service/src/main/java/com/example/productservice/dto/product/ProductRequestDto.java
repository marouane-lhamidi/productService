package com.example.productservice.dto.product;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class ProductRequestDto {
    private String name;
    private String price;
    private String quantity;
    private String description;
    private List<String> tagNames = new ArrayList<>();
    private String categoryName;
    private String brandName;
}
