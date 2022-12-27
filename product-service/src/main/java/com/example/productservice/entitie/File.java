package com.example.productservice.entitie;

import lombok.*;
import javax.persistence.*;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class File {
	@Id
	private String id;
	private String name;
	private String type;
}