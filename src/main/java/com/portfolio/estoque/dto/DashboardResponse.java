package com.portfolio.estoque.dto;
import java.math.BigDecimal;
import java.util.List;
public record DashboardResponse(long produtosCadastrados, long unidadesEmEstoque, BigDecimal valorEstoque,
 List<ProdutoResponse> produtosEstoqueBaixo, List<MovimentacaoResponse> ultimasMovimentacoes,
 long quantidadeEntradas, long quantidadeSaidas) {}
