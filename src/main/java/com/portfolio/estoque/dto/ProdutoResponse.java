package com.portfolio.estoque.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public record ProdutoResponse(Long id, String nome, String descricao, String codigoSku, Long categoriaId,
 String categoriaNome, Integer quantidade, Integer estoqueMinimo, BigDecimal precoCompra, BigDecimal precoVenda,
 LocalDateTime dataCadastro, Boolean ativo, boolean estoqueBaixo) {}
