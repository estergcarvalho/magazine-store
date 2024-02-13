package com.magazinestore.produto.service;

import com.magazinestore.produto.dto.ProdutoRequest;
import com.magazinestore.produto.dto.ProdutoResponse;
import com.magazinestore.produto.exception.ProdutoNaoEncontradoException;
import com.magazinestore.produto.model.Produto;
import com.magazinestore.produto.repository.ProdutoRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private static final String PRODUTO_GUARDA_ROUPA_NOME = "Guarda-Roupa de Madeira Maciça";
    private static final String PRODUTO_GUARDA_ROUPA_DESCRICAO = "Guarda-roupa espaçoso com acabamento em madeira";
    private static final BigDecimal PRODUTO_GUARDA_ROUPA_PRECO = new BigDecimal("1299.99");
    private static final String PRODUTO_GUARDA_ROUPA_MARCA = "Silvia Design";

    @Test
    @DisplayName("Deve cadastrar produto com sucesso")
    public void deveCadastrarProdutoComSucesso() {
        ProdutoRequest guardaRoupaRequest = ProdutoRequest.builder()
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();

        Produto guardaRoupa = Produto.builder()
            .id(1L)
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();

        when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenReturn(guardaRoupa);

        ProdutoResponse guardaRoupaResponse = produtoService.cadastrar(guardaRoupaRequest);

        assertEquals(1L, guardaRoupaResponse.getId().longValue());
        assertEquals(PRODUTO_GUARDA_ROUPA_NOME, guardaRoupaResponse.getNome());
        assertEquals(PRODUTO_GUARDA_ROUPA_DESCRICAO, guardaRoupaResponse.getDescricao());
        assertEquals(PRODUTO_GUARDA_ROUPA_PRECO, guardaRoupaResponse.getPreco());
        assertEquals(PRODUTO_GUARDA_ROUPA_MARCA, guardaRoupaResponse.getMarca());
    }

    @Test
    @DisplayName("Deve buscar produto por id")
    public void deveBuscarProdutoPorId() throws ProdutoNaoEncontradoException {
        Produto guardaRoupa = Produto.builder()
            .id(10L)
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(guardaRoupa));

        ProdutoResponse guardaRoupaResponse = produtoService.buscarPorId(10L);

        assertNotNull(guardaRoupaResponse);
        assertEquals(10L, guardaRoupaResponse.getId().longValue());
        assertEquals(PRODUTO_GUARDA_ROUPA_NOME, guardaRoupaResponse.getNome());
        assertEquals(PRODUTO_GUARDA_ROUPA_DESCRICAO, guardaRoupaResponse.getDescricao());
        assertEquals(PRODUTO_GUARDA_ROUPA_PRECO, guardaRoupaResponse.getPreco());
        assertEquals(PRODUTO_GUARDA_ROUPA_MARCA, guardaRoupaResponse.getMarca());
    }

    @Test
    @DisplayName("Deve lançar exception para produto não encontrado")
    public void deveLancarExceptionProdutoNaoEncontrado() throws Exception {
        Long id = 99999999L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoService.buscarPorId(id));
    }

}