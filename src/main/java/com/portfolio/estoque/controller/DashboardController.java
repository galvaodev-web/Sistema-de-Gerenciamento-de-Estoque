package com.portfolio.estoque.controller;
import com.portfolio.estoque.dto.DashboardResponse;
import com.portfolio.estoque.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/dashboard") @RequiredArgsConstructor
public class DashboardController {
    private final DashboardService service;
    @GetMapping public DashboardResponse obter() { return service.obter(); }
}
