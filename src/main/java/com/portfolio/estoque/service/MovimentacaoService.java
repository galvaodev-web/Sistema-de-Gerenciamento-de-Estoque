package com.portfolio.estoque.service;

import com.portfolio.estoque.dto.*;
import com.portfolio.estoque.exception.RegraNegocioException;
import com.portfolio.estoque.model.*;
import com.portfolio.estoque.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.List;

@Service @RequiredArgsConstructor
public class MovimentacaoService {
    private final MovimentacaoEstoqueRepository repository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;
    @Transactional public MovimentacaoResponse registrar(MovimentacaoRequest dto, TipoMovimentacao tipo) {
        Produto produto = produtoService.buscarEntidade(dto.produtoId());
        if (!produto.getAtivo()) throw new RegraNegocioException("Não é possível movimentar um produto inativo");
        if (tipo == TipoMovimentacao.SAIDA && dto.quantidade() > produto.getQuantidade()) throw new RegraNegocioException("Estoque insuficiente para esta saída");
        int saldo = tipo == TipoMovimentacao.ENTRADA ? produto.getQuantidade() + dto.quantidade() : produto.getQuantidade() - dto.quantidade();
        produto.setQuantidade(saldo); produtoRepository.save(produto);
        MovimentacaoEstoque m = new MovimentacaoEstoque(); m.setProduto(produto); m.setTipo(tipo); m.setQuantidade(dto.quantidade()); m.setObservacao(dto.observacao());
        return map(repository.save(m));
    }
    @Transactional(readOnly=true) public List<MovimentacaoResponse> listar(Long produtoId, TipoMovimentacao tipo, LocalDate inicio, LocalDate fim) {
        List<MovimentacaoEstoque> lista;
        if (produtoId != null) lista = repository.findByProdutoIdOrderByDataMovimentacaoDesc(produtoId);
        else if (tipo != null) lista = repository.findByTipoOrderByDataMovimentacaoDesc(tipo);
        else if (inicio != null && fim != null) lista = repository.findByDataMovimentacaoBetweenOrderByDataMovimentacaoDesc(inicio.atStartOfDay(), fim.plusDays(1).atStartOfDay().minusNanos(1));
        else lista = repository.findAllByOrderByDataMovimentacaoDesc();
        return lista.stream().map(this::map).toList();
    }
    @Transactional(readOnly=true) public List<MovimentacaoResponse> ultimas() { return repository.findTop10ByOrderByDataMovimentacaoDesc().stream().map(this::map).toList(); }
    public MovimentacaoResponse map(MovimentacaoEstoque m) { return new MovimentacaoResponse(m.getId(), m.getProduto().getId(), m.getProduto().getNome(), m.getProduto().getCodigoSku(), m.getTipo(), m.getQuantidade(), m.getDataMovimentacao(), m.getObservacao()); }
}
