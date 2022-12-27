package com.example.productservice.mapper;

import com.example.productservice.dto.tag.TagRequestDto;
import com.example.productservice.dto.tag.TagResponseDto;
import com.example.productservice.entitie.Tag;

public class TagMapper {
    private TagMapper(){}

    public static Tag fromTagRequestDto(TagRequestDto requestDto){
        return Tag.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .build();
    }

    public static TagResponseDto fromTag(Tag requestDto){
        return TagResponseDto.builder()
                .id(String.valueOf(requestDto.getId()))
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .build();
    }
}
