package com.example.productservice.dto.category;

import lombok.*;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class CategoryRequestDto {
    private String name;
    private String description;
}
