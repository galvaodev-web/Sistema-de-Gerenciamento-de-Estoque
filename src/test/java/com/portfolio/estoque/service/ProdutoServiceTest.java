package com.portfolio.estoque.service;

import com.portfolio.estoque.dto.*;
import com.portfolio.estoque.exception.RegraNegocioException;
import com.portfolio.estoque.model.*;
import com.portfolio.estoque.repository.ProdutoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProdutoServiceTest {
    @Mock ProdutoRepository repository; @Mock CategoriaService categoriaService; @InjectMocks ProdutoService service;
    Categoria categoria; ProdutoRequest request;
    @BeforeEach void setup() {
        categoria = new Categoria(); categoria.setId(1L); categoria.setNome("Informática");
        request = new ProdutoRequest("Teclado", "Mecânico", "TEC-01", 1L, 5, 2, new BigDecimal("100"), new BigDecimal("180"), true);
    }
    @Test void deveCadastrarProduto() {
        when(categoriaService.buscarEntidade(1L)).thenReturn(categoria);
        when(repository.findByCodigoSkuIgnoreCase("TEC-01")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(i -> { Produto p=i.getArgument(0); p.setId(1L); p.setDataCadastro(java.time.LocalDateTime.now()); return p; });
        ProdutoResponse salvo = service.criar(request);
        assertEquals("TEC-01", salvo.codigoSku()); assertEquals(5, salvo.quantidade()); verify(repository).save(any());
    }
    @Test void deveBloquearSkuDuplicado() {
        Produto existente = new Produto(); existente.setId(9L);
        when(repository.findByCodigoSkuIgnoreCase("TEC-01")).thenReturn(Optional.of(existente));
        assertThrows(RegraNegocioException.class, () -> service.criar(request)); verify(repository, never()).save(any());
    }
    @Test void deveIdentificarEstoqueBaixo() {
        Produto p = produto(2, 2); when(repository.findEstoqueBaixo()).thenReturn(List.of(p));
        assertTrue(service.estoqueBaixo().getFirst().estoqueBaixo());
    }
    private Produto produto(int qtd, int minimo) { Produto p=new Produto(); p.setId(1L); p.setNome("Teclado"); p.setCodigoSku("TEC-01"); p.setCategoria(categoria); p.setQuantidade(qtd); p.setEstoqueMinimo(minimo); p.setPrecoCompra(BigDecimal.TEN); p.setPrecoVenda(BigDecimal.TEN); p.setAtivo(true); return p; }
}
