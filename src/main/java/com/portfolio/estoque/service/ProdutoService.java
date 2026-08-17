package com.portfolio.estoque.service;

import com.portfolio.estoque.dto.*;
import com.portfolio.estoque.exception.*;
import com.portfolio.estoque.model.*;
import com.portfolio.estoque.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;
    private final CategoriaService categoriaService;
    @Transactional public ProdutoResponse criar(ProdutoRequest dto) {
        validarSkuNovo(dto.codigoSku(), null);
        Produto p = new Produto(); preencher(p, dto); return map(repository.save(p));
    }
    @Transactional(readOnly=true) public List<ProdutoResponse> listar(String nome) {
        List<Produto> itens = nome == null || nome.isBlank() ? repository.findByAtivoTrueOrderByNome() : repository.findByNomeContainingIgnoreCaseAndAtivoTrueOrderByNome(nome);
        return itens.stream().map(this::map).toList();
    }
    @Transactional(readOnly=true) public ProdutoResponse buscar(Long id) { return map(buscarEntidade(id)); }
    @Transactional(readOnly=true) public ProdutoResponse buscarPorSku(String sku) { return map(repository.findByCodigoSkuIgnoreCase(sku).orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado"))); }
    @Transactional(readOnly=true) public List<ProdutoResponse> estoqueBaixo() { return repository.findEstoqueBaixo().stream().map(this::map).toList(); }
    @Transactional public ProdutoResponse atualizar(Long id, ProdutoRequest dto) {
        Produto p = buscarEntidade(id); validarSkuNovo(dto.codigoSku(), id); preencher(p, dto); return map(repository.save(p));
    }
    @Transactional public void desativar(Long id) { Produto p = buscarEntidade(id); p.setAtivo(false); repository.save(p); }
    @Transactional(readOnly=true) public Produto buscarEntidade(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado")); }
    private void validarSkuNovo(String sku, Long id) { repository.findByCodigoSkuIgnoreCase(sku.trim()).filter(p -> !p.getId().equals(id)).ifPresent(p -> { throw new RegraNegocioException("Já existe um produto com este SKU"); }); }
    private void preencher(Produto p, ProdutoRequest d) {
        p.setNome(d.nome().trim()); p.setDescricao(d.descricao()); p.setCodigoSku(d.codigoSku().trim().toUpperCase());
        p.setCategoria(categoriaService.buscarEntidade(d.categoriaId())); p.setQuantidade(d.quantidade()); p.setEstoqueMinimo(d.estoqueMinimo());
        p.setPrecoCompra(d.precoCompra()); p.setPrecoVenda(d.precoVenda()); p.setAtivo(d.ativo() == null ? true : d.ativo());
    }
    public ProdutoResponse map(Produto p) { return new ProdutoResponse(p.getId(), p.getNome(), p.getDescricao(), p.getCodigoSku(), p.getCategoria().getId(), p.getCategoria().getNome(), p.getQuantidade(), p.getEstoqueMinimo(), p.getPrecoCompra(), p.getPrecoVenda(), p.getDataCadastro(), p.getAtivo(), p.getQuantidade() <= p.getEstoqueMinimo()); }
}
