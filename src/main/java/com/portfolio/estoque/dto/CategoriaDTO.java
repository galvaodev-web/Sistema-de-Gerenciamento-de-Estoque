package com.portfolio.estoque.dto;
import jakarta.validation.constraints.*;
public record CategoriaDTO(Long id, @NotBlank(message="O nome é obrigatório") @Size(max=100) String nome, @Size(max=500) String descricao) {}
