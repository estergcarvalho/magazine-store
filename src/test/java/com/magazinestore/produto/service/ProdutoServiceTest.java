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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        ProdutoRequest produtoRequest = ProdutoRequest.builder()
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();

        Produto produtoEsperado = Produto.builder()
            .id(1L)
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();

        when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenReturn(produtoEsperado);

        ProdutoResponse produtoResponse = produtoService.cadastrar(produtoRequest);

        assertEquals(1L, produtoResponse.getId().longValue());
        assertEquals(PRODUTO_GUARDA_ROUPA_NOME, produtoResponse.getNome());
        assertEquals(PRODUTO_GUARDA_ROUPA_DESCRICAO, produtoResponse.getDescricao());
        assertEquals(PRODUTO_GUARDA_ROUPA_PRECO, produtoResponse.getPreco());
        assertEquals(PRODUTO_GUARDA_ROUPA_MARCA, produtoResponse.getMarca());
    }

    @Test
    @DisplayName("Deve buscar produto por id")
    public void deveBuscarProdutoPorId() throws ProdutoNaoEncontradoException {
        Long idDoProduto = 10L;

        Produto produto = Produto.builder()
            .id(idDoProduto)
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));

        List<ProdutoResponse> listaDeProdutos = produtoService.buscarPorId(idDoProduto);

        assertFalse(listaDeProdutos.isEmpty());
        assertEquals(1, listaDeProdutos.size());

        ProdutoResponse produtoResponse = listaDeProdutos.get(0);

        assertEquals(idDoProduto, produtoResponse.getId());
        assertEquals(PRODUTO_GUARDA_ROUPA_NOME, produtoResponse.getNome());
        assertEquals(PRODUTO_GUARDA_ROUPA_DESCRICAO, produtoResponse.getDescricao());
        assertEquals(PRODUTO_GUARDA_ROUPA_PRECO, produtoResponse.getPreco());
        assertEquals(PRODUTO_GUARDA_ROUPA_MARCA, produtoResponse.getMarca());
    }

    @Test
    @DisplayName("Deve retornar status 404 Not Found para produto não encontrado")
    public void deveRetornarStatus404ProdutoNaoEncontrado() throws Exception {
        Long idDoProduto = 99999999L;

        when(produtoRepository.findById(idDoProduto)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoService.buscarPorId(idDoProduto));
    }

}