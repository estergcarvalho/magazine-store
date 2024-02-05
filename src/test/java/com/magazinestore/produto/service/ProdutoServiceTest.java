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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProdutoServiceTest {

    @InjectMocks
    ProdutoService produtoService;

    @Mock
    ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Deve cadastrar produto com sucesso")
    public void deveCadastrarProdutoComSucesso() {
        ProdutoRequest produtoRequest = ProdutoRequest.builder()
            .nome("Guarda-Roupa de Madeira Maciça")
            .descricao("Guarda-roupa espaçoso com acabamento em madeira maciça, perfeito para organizar suas roupas.")
            .preco(new BigDecimal("1299.99"))
            .marca("Silvia Design")
            .build();

        Produto produtoEsperado = Produto.builder()
            .id(1L)
            .nome("Guarda-Roupa de Madeira Maciça")
            .descricao("Guarda-roupa espaçoso com acabamento em madeira maciça, perfeito para organizar suas roupas.")
            .preco(new BigDecimal("1299.99"))
            .marca("Silvia Design")
            .build();

        when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenReturn(produtoEsperado);

        ProdutoResponse produtoResponse = produtoService.cadastrar(produtoRequest);

        assertEquals(1L, produtoResponse.getId().longValue());
        assertEquals("Guarda-Roupa de Madeira Maciça", produtoResponse.getNome());
        assertEquals("Guarda-roupa espaçoso com acabamento em madeira maciça, perfeito para organizar suas roupas.", produtoResponse.getDescricao());
        assertEquals(new BigDecimal("1299.99"), produtoResponse.getPreco());
        assertEquals("Silvia Design", produtoResponse.getMarca());
    }

}