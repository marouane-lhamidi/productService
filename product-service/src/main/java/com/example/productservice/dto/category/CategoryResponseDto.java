package com.example.productservice.dto.category;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CategoryResponseDto {
    private String id;
    private String name;
    private String description;

}
