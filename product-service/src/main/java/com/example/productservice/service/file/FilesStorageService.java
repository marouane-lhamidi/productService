package com.example.productservice.service.file;

import com.example.productservice.entitie.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {
  public void init();

  public File save(MultipartFile file);

  public Resource load(String filename);
  
  public boolean delete(String filename);

  public void deleteAll();

  public Stream<Path> loadAll();
}
