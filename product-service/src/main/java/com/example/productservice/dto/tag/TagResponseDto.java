package com.example.productservice.dto.tag;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class TagResponseDto {
    private String id;
    private String name;
    private String description;

}
