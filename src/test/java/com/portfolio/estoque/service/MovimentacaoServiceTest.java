package com.portfolio.estoque.service;

import com.portfolio.estoque.dto.MovimentacaoRequest;
import com.portfolio.estoque.exception.RegraNegocioException;
import com.portfolio.estoque.model.*;
import com.portfolio.estoque.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class MovimentacaoServiceTest {
    @Mock MovimentacaoEstoqueRepository repository; @Mock ProdutoRepository produtoRepository; @Mock ProdutoService produtoService;
    @InjectMocks MovimentacaoService service; Produto produto;
    @BeforeEach void setup() {
        produto=new Produto(); produto.setId(1L); produto.setNome("Mouse"); produto.setCodigoSku("MOU-01"); produto.setQuantidade(10); produto.setAtivo(true);
        when(produtoService.buscarEntidade(1L)).thenReturn(produto);
        lenient().when(repository.save(any())).thenAnswer(i -> { MovimentacaoEstoque m=i.getArgument(0); m.setId(1L); m.setDataMovimentacao(java.time.LocalDateTime.now()); return m; });
    }
    @Test void deveRegistrarEntrada() { service.registrar(new MovimentacaoRequest(1L, 5, null), TipoMovimentacao.ENTRADA); assertEquals(15, produto.getQuantidade()); }
    @Test void deveRegistrarSaida() { service.registrar(new MovimentacaoRequest(1L, 4, null), TipoMovimentacao.SAIDA); assertEquals(6, produto.getQuantidade()); }
    @Test void deveBloquearSaidaSemSaldo() {
        assertThrows(RegraNegocioException.class, () -> service.registrar(new MovimentacaoRequest(1L, 11, null), TipoMovimentacao.SAIDA));
        assertEquals(10, produto.getQuantidade()); verify(repository, never()).save(any());
    }
}
