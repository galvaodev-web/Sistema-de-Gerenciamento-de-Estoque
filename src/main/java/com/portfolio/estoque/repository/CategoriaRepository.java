package com.portfolio.estoque.repository;
import com.portfolio.estoque.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoriaRepository extends JpaRepository<Categoria, Long> { boolean existsByNomeIgnoreCase(String nome); }
