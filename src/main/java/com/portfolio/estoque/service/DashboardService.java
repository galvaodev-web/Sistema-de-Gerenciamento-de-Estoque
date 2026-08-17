package com.portfolio.estoque.service;
import com.portfolio.estoque.dto.*;
import com.portfolio.estoque.model.TipoMovimentacao;
import com.portfolio.estoque.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class DashboardService {
    private final ProdutoRepository produtos; private final MovimentacaoEstoqueRepository movimentacoes;
    private final ProdutoService produtoService; private final MovimentacaoService movimentacaoService;
    @Transactional(readOnly=true) public DashboardResponse obter() {
        return new DashboardResponse(produtos.countByAtivoTrue(), produtos.totalUnidades(), produtos.valorEstoque(),
            produtos.findEstoqueBaixo().stream().map(produtoService::map).toList(), movimentacaoService.ultimas(),
            movimentacoes.countByTipo(TipoMovimentacao.ENTRADA), movimentacoes.countByTipo(TipoMovimentacao.SAIDA));
    }
}
