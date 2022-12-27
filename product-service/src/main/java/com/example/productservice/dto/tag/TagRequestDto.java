package com.example.productservice.dto.tag;

import lombok.*;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class TagRequestDto {
    private String name;
    private String description;
}
