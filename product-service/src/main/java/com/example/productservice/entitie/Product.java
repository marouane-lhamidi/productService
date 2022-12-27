package com.example.productservice.entitie;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String description;
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<File> images = new ArrayList<>();
    @ManyToOne
    private Category category;
    @ManyToOne
    private Brand brand;

}
