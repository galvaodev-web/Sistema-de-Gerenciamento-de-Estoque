package com.portfolio.estoque.service;

import com.portfolio.estoque.dto.CategoriaDTO;
import com.portfolio.estoque.exception.*;
import com.portfolio.estoque.model.Categoria;
import com.portfolio.estoque.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository repository;
    private final ProdutoRepository produtoRepository;
    @Transactional public CategoriaDTO criar(CategoriaDTO dto) {
        if (repository.existsByNomeIgnoreCase(dto.nome().trim())) throw new RegraNegocioException("Já existe uma categoria com este nome");
        Categoria c = new Categoria(); c.setNome(dto.nome().trim()); c.setDescricao(dto.descricao()); return map(repository.save(c));
    }
    @Transactional(readOnly=true) public List<CategoriaDTO> listar() { return repository.findAll().stream().map(this::map).toList(); }
    @Transactional public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria c = buscarEntidade(id);
        if (!c.getNome().equalsIgnoreCase(dto.nome().trim()) && repository.existsByNomeIgnoreCase(dto.nome().trim())) throw new RegraNegocioException("Já existe uma categoria com este nome");
        c.setNome(dto.nome().trim()); c.setDescricao(dto.descricao()); return map(repository.save(c));
    }
    @Transactional public void excluir(Long id) {
        Categoria c = buscarEntidade(id);
        if (produtoRepository.existsByCategoriaId(id)) throw new RegraNegocioException("A categoria possui produtos vinculados");
        repository.delete(c);
    }
    @Transactional(readOnly=true) public Categoria buscarEntidade(Long id) { return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada")); }
    private CategoriaDTO map(Categoria c) { return new CategoriaDTO(c.getId(), c.getNome(), c.getDescricao()); }
}
