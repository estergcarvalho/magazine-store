package com.magazinestore.produto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magazinestore.produto.dto.ProdutoRequest;
import com.magazinestore.produto.dto.ProdutoResponse;
import com.magazinestore.produto.model.Produto;
import com.magazinestore.produto.repository.ProdutoRepository;
import com.magazinestore.produto.service.ProdutoService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ProdutoRepository produtoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PRODUTO_TELEVISAO_NOME = "Smart TV 55” UHD 4K LED LG";
    private static final String PRODUTO_TELEVISAO_DESCRICAO = "Ela possui resolução UHD 4K com tecnologia LED";
    private static final BigDecimal PRODUTO_TELEVISAO_PRECO = new BigDecimal("2599.0");
    private static final String PRODUTO_TELEVISAO_MARCA = "LG";

    @Test
    @DisplayName("Deve cadastrar um produto com sucesso")
    public void deveCadastrarProduto() throws Exception {
        ProdutoRequest produtoRequest = ProdutoRequest.builder()
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        ProdutoResponse produtoResponse = ProdutoResponse.builder()
            .id(1L)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        when(produtoService.cadastrar(any(ProdutoRequest.class))).thenReturn(produtoResponse);
        when(produtoRepository.save(any())).thenReturn(produtoResponse);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.nome").value(PRODUTO_TELEVISAO_NOME))
            .andExpect(jsonPath("$.descricao").value(PRODUTO_TELEVISAO_DESCRICAO))
            .andExpect(jsonPath("$.preco").value(PRODUTO_TELEVISAO_PRECO))
            .andExpect(jsonPath("$.marca").value(PRODUTO_TELEVISAO_MARCA));
    }

    @Test
    @DisplayName("Deve buscar produto por id")
    public void deveBuscarProdutoPorId() throws Exception {
        Produto produto = Produto.builder()
            .id(5L)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        ProdutoResponse produtoResponse = ProdutoResponse.builder()
            .id(produto.getId())
            .nome(produto.getNome())
            .descricao(produto.getDescricao())
            .preco(produto.getPreco())
            .marca(produto.getMarca())
            .build();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoService.buscarPorId(5L)).thenReturn(Collections.singletonList(produtoResponse));

        mockMvc.perform(get("/produtos/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(5L))
            .andExpect(jsonPath("$[0].nome").value(PRODUTO_TELEVISAO_NOME))
            .andExpect(jsonPath("$[0].descricao").value(PRODUTO_TELEVISAO_DESCRICAO))
            .andExpect(jsonPath("$[0].preco").value(PRODUTO_TELEVISAO_PRECO))
            .andExpect(jsonPath("$[0].marca").value(PRODUTO_TELEVISAO_MARCA));
    }

    @Test
    @DisplayName("Deve lançar exception para produto não encontrado")
    public void deveLancarExceptionProdutoNaoEncontrado() throws Exception {
        Long idNaoExistente = 9999999L;

        when(produtoRepository.findById(idNaoExistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/produtos/{id}", idNaoExistente)
                .contentType("application/json"))
            .andExpect(status().isNotFound());
    }

}