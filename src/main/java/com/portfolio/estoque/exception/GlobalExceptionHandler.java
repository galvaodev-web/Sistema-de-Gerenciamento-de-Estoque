package com.portfolio.estoque.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    ResponseEntity<ApiError> naoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest req) {
        return resposta(HttpStatus.NOT_FOUND, ex.getMessage(), req, null);
    }
    @ExceptionHandler(RegraNegocioException.class)
    ResponseEntity<ApiError> conflito(RegraNegocioException ex, HttpServletRequest req) {
        return resposta(HttpStatus.CONFLICT, ex.getMessage(), req, null);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> validacao(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String,String> campos = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> campos.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return resposta(HttpStatus.BAD_REQUEST, "Dados inválidos", req, campos);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ApiError> integridade(DataIntegrityViolationException ex, HttpServletRequest req) {
        return resposta(HttpStatus.CONFLICT, "Operação não permitida por integridade dos dados", req, null);
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> inesperado(Exception ex, HttpServletRequest req) {
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno inesperado", req, null);
    }
    private ResponseEntity<ApiError> resposta(HttpStatus status, String mensagem, HttpServletRequest req, Map<String,String> campos) {
        return ResponseEntity.status(status).body(new ApiError(LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensagem, req.getRequestURI(), campos));
    }
}
