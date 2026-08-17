package com.portfolio.estoque.controller;
import com.portfolio.estoque.dto.*;
import com.portfolio.estoque.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/produtos") @RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService service;
    @GetMapping public List<ProdutoResponse> listar(@RequestParam(required=false) String nome) { return service.listar(nome); }
    @GetMapping("/{id}") public ProdutoResponse buscar(@PathVariable Long id) { return service.buscar(id); }
    @GetMapping("/sku/{sku}") public ProdutoResponse buscarSku(@PathVariable String sku) { return service.buscarPorSku(sku); }
    @GetMapping("/estoque-baixo") public List<ProdutoResponse> estoqueBaixo() { return service.estoqueBaixo(); }
    @PostMapping public ResponseEntity<ProdutoResponse> criar(@Valid @RequestBody ProdutoRequest dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto)); }
    @PutMapping("/{id}") public ProdutoResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest dto) { return service.atualizar(id, dto); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void desativar(@PathVariable Long id) { service.desativar(id); }
}
