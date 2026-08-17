package com.portfolio.estoque.repository;
import com.portfolio.estoque.model.Produto;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.*;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByCodigoSkuIgnoreCase(String sku);
    boolean existsByCodigoSkuIgnoreCase(String sku);
    boolean existsByCategoriaId(Long categoriaId);
    List<Produto> findByNomeContainingIgnoreCaseAndAtivoTrueOrderByNome(String nome);
    List<Produto> findByAtivoTrueOrderByNome();
    @Query("select p from Produto p where p.ativo = true and p.quantidade <= p.estoqueMinimo order by p.quantidade")
    List<Produto> findEstoqueBaixo();
    @Query("select coalesce(sum(p.quantidade), 0) from Produto p where p.ativo = true")
    Long totalUnidades();
    @Query("select coalesce(sum(p.quantidade * p.precoCompra), 0) from Produto p where p.ativo = true")
    BigDecimal valorEstoque();
    long countByAtivoTrue();
}
