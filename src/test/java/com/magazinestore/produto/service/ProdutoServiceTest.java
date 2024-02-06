package com.magazinestore.produto.service;

import com.magazinestore.produto.dto.ProdutoRequest;
import com.magazinestore.produto.dto.ProdutoResponse;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
public class
ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private static final String PRODUTO_GUARDA_ROUPA_NOME = "Guarda-Roupa de Madeira Maciça";
    private static final String PRODUTO_GUARDA_ROUPA_DESCRICAO = "Guarda-roupa espaçoso com acabamento em madeira";
    private static final BigDecimal PRODUTO_GUARDA_ROUPA_PRECO = new BigDecimal("1299.99");
    private static final String PRODUTO_GUARDA_ROUPA_MARCA = "Silvia Design";

    private static final String PRODUTO_TELEVISAO_NOME = "Smart TV 55” UHD 4K LED LG";
    private static final String PRODUTO_TELEVISAO_DESCRICAO = "Ela possui resolução UHD 4K com tecnologia LED";
    private static final BigDecimal PRODUTO_TELEVISAO_PRECO = new BigDecimal("2599.0");
    private static final String PRODUTO_TELEVISAO_MARCA = "LG";

    @Test
    @DisplayName("Deve cadastrar produto")
    public void deveCadastrarProduto() {
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
    @DisplayName("Deve listar produtos")
    public void deveListarProdutos() {
        List<Produto> produtosBanco = new ArrayList<>();

        Produto produto1 = Produto.builder()
            .id(1L)
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();
        produtosBanco.add(produto1);

        Produto produto2 = Produto.builder()
            .id(2L)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();
        produtosBanco.add(produto2);

        when(produtoRepository.findAll()).thenReturn(produtosBanco);

        List<ProdutoResponse> produtoResponse = produtoService.listar();

        ProdutoResponse produtoResponse1 = produtoResponse.get(0);
        assertEquals(1L, produtoResponse1.getId().longValue());
        assertEquals(PRODUTO_GUARDA_ROUPA_NOME, produtoResponse1.getNome());
        assertEquals(PRODUTO_GUARDA_ROUPA_DESCRICAO, produtoResponse1.getDescricao());
        assertEquals(PRODUTO_GUARDA_ROUPA_MARCA, produtoResponse1.getMarca());
        assertEquals(PRODUTO_GUARDA_ROUPA_PRECO, produtoResponse1.getPreco());

        ProdutoResponse produtoResponse2 = produtoResponse.get(1);
        assertEquals(2L, produtoResponse2.getId().longValue());
        assertEquals(PRODUTO_TELEVISAO_NOME, produtoResponse2.getNome());
        assertEquals(PRODUTO_TELEVISAO_DESCRICAO, produtoResponse2.getDescricao());
        assertEquals(PRODUTO_TELEVISAO_MARCA, produtoResponse2.getMarca());
        assertEquals(PRODUTO_TELEVISAO_PRECO, produtoResponse2.getPreco());
    }

}