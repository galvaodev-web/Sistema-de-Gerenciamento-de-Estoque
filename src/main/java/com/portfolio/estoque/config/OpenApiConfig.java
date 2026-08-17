package com.portfolio.estoque.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;
@Configuration
public class OpenApiConfig {
    @Bean OpenAPI estoqueOpenAPI() { return new OpenAPI().info(new Info().title("API de Gerenciamento de Estoque").version("1.0.0").description("Produtos, categorias, movimentações e indicadores de estoque.")); }
}
