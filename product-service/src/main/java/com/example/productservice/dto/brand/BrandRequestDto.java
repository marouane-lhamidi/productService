package com.example.productservice.dto.brand;

import lombok.*;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class BrandRequestDto {
    private String name;
    private String description;
}
