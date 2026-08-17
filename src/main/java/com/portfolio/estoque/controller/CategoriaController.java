package com.portfolio.estoque.controller;
import com.portfolio.estoque.dto.CategoriaDTO;
import com.portfolio.estoque.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/categorias") @RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService service;
    @GetMapping public List<CategoriaDTO> listar() { return service.listar(); }
    @PostMapping public ResponseEntity<CategoriaDTO> criar(@Valid @RequestBody CategoriaDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto)); }
    @PutMapping("/{id}") public CategoriaDTO atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) { return service.atualizar(id, dto); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void excluir(@PathVariable Long id) { service.excluir(id); }
}
