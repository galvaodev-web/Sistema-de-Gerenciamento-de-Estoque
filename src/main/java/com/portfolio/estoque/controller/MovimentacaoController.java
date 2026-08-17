package com.portfolio.estoque.controller;
import com.portfolio.estoque.dto.*;
import com.portfolio.estoque.model.TipoMovimentacao;
import com.portfolio.estoque.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController @RequestMapping("/api/movimentacoes") @RequiredArgsConstructor
public class MovimentacaoController {
    private final MovimentacaoService service;
    @PostMapping("/entrada") public ResponseEntity<MovimentacaoResponse> entrada(@Valid @RequestBody MovimentacaoRequest dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto, TipoMovimentacao.ENTRADA)); }
    @PostMapping("/saida") public ResponseEntity<MovimentacaoResponse> saida(@Valid @RequestBody MovimentacaoRequest dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto, TipoMovimentacao.SAIDA)); }
    @GetMapping public List<MovimentacaoResponse> listar(@RequestParam(required=false) Long produtoId, @RequestParam(required=false) TipoMovimentacao tipo,
      @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate inicio,
      @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fim) { return service.listar(produtoId, tipo, inicio, fim); }
}
