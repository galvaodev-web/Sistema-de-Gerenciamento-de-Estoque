package com.portfolio.estoque.repository;
import com.portfolio.estoque.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    List<MovimentacaoEstoque> findTop10ByOrderByDataMovimentacaoDesc();
    List<MovimentacaoEstoque> findAllByOrderByDataMovimentacaoDesc();
    List<MovimentacaoEstoque> findByProdutoIdOrderByDataMovimentacaoDesc(Long produtoId);
    List<MovimentacaoEstoque> findByTipoOrderByDataMovimentacaoDesc(TipoMovimentacao tipo);
    List<MovimentacaoEstoque> findByDataMovimentacaoBetweenOrderByDataMovimentacaoDesc(LocalDateTime inicio, LocalDateTime fim);
    long countByTipo(TipoMovimentacao tipo);
}
