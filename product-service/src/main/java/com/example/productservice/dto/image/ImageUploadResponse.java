package com.example.productservice.dto.image;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ImageUploadResponse {
    private String id;
    private String image;
}
