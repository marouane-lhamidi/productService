package com.example.productservice.controller;

import com.example.productservice.common.utiles.ApisPath;
import com.example.productservice.dto.tag.TagRequestDto;
import com.example.productservice.dto.tag.TagResponseDto;
import com.example.productservice.service.tag.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor @CrossOrigin("*")
public class TagController {
    TagService service;

    @GetMapping(ApisPath.GET_ALL_TAGS)
    public ResponseEntity<List<TagResponseDto>> getTags(){
        return ResponseEntity.status(HttpStatus.OK).body(service.tagsList());
    }

    @GetMapping(ApisPath.GET_TAG_BY_NAME)
    public ResponseEntity<TagResponseDto> getTagByName(@PathVariable(name = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(service.tagByName(name));
    }

    @PostMapping(ApisPath.ADD_TAG)
    public ResponseEntity<TagResponseDto> addTags(@RequestBody TagRequestDto tagRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addTag(tagRequestDto));
    }

    @PutMapping(ApisPath.UPDATE_TAG)
    public ResponseEntity<TagResponseDto> updateTags(@RequestBody TagRequestDto tagRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateTag(tagRequestDto));
    }

    @DeleteMapping(ApisPath.DELETE_TAG)
    public void deleteTags(@PathVariable(name = "id") Long id){
        service.deleteTag(id);
    }

}
