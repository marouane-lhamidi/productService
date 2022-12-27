package com.example.productservice.service.tag;

import com.example.productservice.dto.tag.TagRequestDto;
import com.example.productservice.dto.tag.TagResponseDto;

import java.util.List;

public interface TagService {
    List<TagResponseDto> tagsList();
    TagResponseDto tagByName(String name);
    TagResponseDto addTag(TagRequestDto tagRequestDto);
    TagResponseDto updateTag(TagRequestDto tagRequestDto);
    void deleteTag(Long id);

}
