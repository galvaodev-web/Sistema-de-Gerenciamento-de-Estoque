package com.portfolio.estoque.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "produtos")
@Getter @Setter @NoArgsConstructor
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 150) private String nome;
    @Column(length = 1000) private String descricao;
    @Column(nullable = false, unique = true, length = 60) private String codigoSku;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "categoria_id") private Categoria categoria;
    @Column(nullable = false) private Integer quantidade;
    @Column(nullable = false) private Integer estoqueMinimo;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal precoCompra;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal precoVenda;
    @Column(nullable = false, updatable = false) private LocalDateTime dataCadastro;
    @Column(nullable = false) private Boolean ativo;

    @PrePersist void prepararCadastro() {
        dataCadastro = dataCadastro == null ? LocalDateTime.now() : dataCadastro;
        ativo = ativo == null ? Boolean.TRUE : ativo;
    }
}
