package com.example.productservice.dto.brand;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class BrandResponseDto {
    private String id;
    private String name;
    private String description;

}
