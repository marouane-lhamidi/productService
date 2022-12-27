package com.example.productservice;

import com.example.productservice.service.file.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ProductServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(FilesStorageService service){
        return args -> {
            //storageService.deleteAll();
            service.init();
        };
    }
}
