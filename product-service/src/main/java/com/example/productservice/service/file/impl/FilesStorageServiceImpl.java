package com.example.productservice.service.file.impl;

import com.example.productservice.common.utiles.MessagesCode;
import com.example.productservice.entitie.File;
import com.example.productservice.repository.FileRepository;
import com.example.productservice.service.file.FilesStorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;


@Service @Transactional @AllArgsConstructor
public class FilesStorageServiceImpl implements FilesStorageService {
  FileRepository fileRepository;

  private final Path root = Paths.get("uploads");

  @Override
  public void init() {
    try {
      Files.createDirectories(root);

    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public File save(MultipartFile file) {
    String id = UUID.randomUUID().toString();
      String ext1= FilenameUtils.getExtension(file.getOriginalFilename());
    try {

      Files.copy(file.getInputStream(), this.root.resolve(id+"."+ext1));

      return fileRepository.save(File.builder()
              .id(id+"."+ext1)
              .name(file.getOriginalFilename())
              .type(file.getContentType())
              .build());
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException(MessagesCode.READING_FILE_PROBLEM);
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public boolean delete(String filename) {
    try {
      Path file = root.resolve(filename);
      return Files.deleteIfExists(file);
    } catch (IOException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }

}
