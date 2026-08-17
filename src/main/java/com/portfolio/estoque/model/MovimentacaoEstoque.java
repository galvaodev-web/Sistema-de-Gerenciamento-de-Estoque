package com.portfolio.estoque.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "movimentacoes_estoque")
@Getter @Setter @NoArgsConstructor
public class MovimentacaoEstoque {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "produto_id") private Produto produto;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 10) private TipoMovimentacao tipo;
    @Column(nullable = false) private Integer quantidade;
    @Column(nullable = false, updatable = false) private LocalDateTime dataMovimentacao;
    @Column(length = 500) private String observacao;

    @PrePersist void prepararData() { dataMovimentacao = dataMovimentacao == null ? LocalDateTime.now() : dataMovimentacao; }
}
