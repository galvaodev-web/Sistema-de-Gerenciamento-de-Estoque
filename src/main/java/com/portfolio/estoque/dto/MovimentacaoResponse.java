package com.portfolio.estoque.dto;
import com.portfolio.estoque.model.TipoMovimentacao;
import java.time.LocalDateTime;
public record MovimentacaoResponse(Long id, Long produtoId, String produtoNome, String produtoSku,
 TipoMovimentacao tipo, Integer quantidade, LocalDateTime dataMovimentacao, String observacao) {}
