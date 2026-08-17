package com.portfolio.estoque.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "categorias")
@Getter @Setter @NoArgsConstructor
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 100) private String nome;
    @Column(length = 500) private String descricao;
}
