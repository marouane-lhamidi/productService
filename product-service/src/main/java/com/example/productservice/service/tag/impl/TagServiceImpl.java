package com.example.productservice.service.tag.impl;

import com.example.productservice.common.exception.InvalidInputException;
import com.example.productservice.common.utiles.MessagesCode;
import com.example.productservice.common.utiles.Utiles;
import com.example.productservice.dto.tag.TagRequestDto;
import com.example.productservice.dto.tag.TagResponseDto;
import com.example.productservice.entitie.Tag;
import com.example.productservice.mapper.TagMapper;
import com.example.productservice.repository.TagRepository;
import com.example.productservice.service.tag.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Transactional @AllArgsConstructor
public class TagServiceImpl implements TagService {
    TagRepository repository;
    @Override
    public List<TagResponseDto> tagsList() {
        List<Tag> tags = repository.findAll();
        return tags.stream().map(TagMapper::fromTag).collect(Collectors.toList());
    }

    @Override
    public TagResponseDto tagByName(String name) {
        Tag tag = repository.findByName(name.toLowerCase()).orElseThrow(() -> new InvalidInputException(MessagesCode.TAG_NOT_EXIST));
        return TagMapper.fromTag(tag);
    }

    @Override
    public TagResponseDto addTag(TagRequestDto tagRequestDto) {
        if(Utiles.isNullOrEmpty(tagRequestDto.getName()))
            throw new InvalidInputException(MessagesCode.INPUT_NAME_IS_NULL);
        tagRequestDto.setName(tagRequestDto.getName().toLowerCase());

        Optional<Tag> tag = repository.findByName(tagRequestDto.getName());
        if (tag.isPresent())
            throw new InvalidInputException(MessagesCode.TAG_ALREADY_EXIST);

        Tag tagToSave = repository.save(TagMapper.fromTagRequestDto(tagRequestDto));
        return TagMapper.fromTag(tagToSave);
    }

    @Override
    public TagResponseDto updateTag(TagRequestDto tagRequestDto) {
        if(Utiles.isNullOrEmpty(tagRequestDto.getName()))
            throw new InvalidInputException(MessagesCode.INPUT_NAME_IS_NULL);
        tagRequestDto.setName(tagRequestDto.getName().toLowerCase());

        Optional<Tag> tag = repository.findByName(tagRequestDto.getName());
        if (!tag.isPresent())
            throw new InvalidInputException(MessagesCode.TAG_NOT_EXIST);
        tag.get().setName(tagRequestDto.getName() == null ? tag.get().getName() : tagRequestDto.getName());
        tag.get().setDescription(tagRequestDto.getDescription() == null ? tag.get().getDescription() : tagRequestDto.getDescription());

        Tag tagToSave = repository.save(tag.get());
        return TagMapper.fromTag(tagToSave);
    }

    @Override
    public void deleteTag(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
        }else {
            throw new InvalidInputException(MessagesCode.TAG_NOT_EXIST);
        }

    }
}
