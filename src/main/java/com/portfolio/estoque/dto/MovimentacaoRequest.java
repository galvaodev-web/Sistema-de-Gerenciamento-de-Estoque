package com.portfolio.estoque.dto;
import jakarta.validation.constraints.*;
public record MovimentacaoRequest(@NotNull(message="O produto é obrigatório") Long produtoId,
 @NotNull @Positive(message="A quantidade deve ser maior que zero") Integer quantidade, @Size(max=500) String observacao) {}
