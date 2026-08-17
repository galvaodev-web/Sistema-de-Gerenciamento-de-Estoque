package com.portfolio.estoque.exception;
import java.time.LocalDateTime;
import java.util.Map;
public record ApiError(LocalDateTime timestamp, int status, String erro, String mensagem, String caminho, Map<String,String> campos) {}
