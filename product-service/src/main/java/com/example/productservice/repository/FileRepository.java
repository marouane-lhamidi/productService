package com.example.productservice.repository;

import com.example.productservice.entitie.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, String> {
	Optional<File> findByName(String name);
}
