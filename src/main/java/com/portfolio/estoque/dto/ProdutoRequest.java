package com.portfolio.estoque.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProdutoRequest(
    @NotBlank(message="O nome é obrigatório") @Size(max=150) String nome,
    @Size(max=1000) String descricao,
    @NotBlank(message="O SKU é obrigatório") @Size(max=60) String codigoSku,
    @NotNull(message="A categoria é obrigatória") Long categoriaId,
    @NotNull @PositiveOrZero(message="A quantidade não pode ser negativa") Integer quantidade,
    @NotNull @PositiveOrZero(message="O estoque mínimo não pode ser negativo") Integer estoqueMinimo,
    @NotNull @PositiveOrZero(message="O preço de compra não pode ser negativo") BigDecimal precoCompra,
    @NotNull @PositiveOrZero(message="O preço de venda não pode ser negativo") BigDecimal precoVenda,
    Boolean ativo) {}
