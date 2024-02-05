package com.magazinestore.produto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magazinestore.produto.dto.ProdutoRequest;
import com.magazinestore.produto.dto.ProdutoResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

    @Test
    @DisplayName("Deve cadastrar um produto com sucesso")
    public void deveCadastrarProduto() throws Exception {
        ProdutoRequest produtoRequest = ProdutoRequest.builder()
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(new BigDecimal("2599.0"))
            .marca("LG")
            .build();

        ProdutoResponse produtoResponse = ProdutoResponse.builder()
            .id(1L)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(new BigDecimal("2599.0"))
            .marca("LG")
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
            .andExpect(jsonPath("$.preco").value("2599.0"))
            .andExpect(jsonPath("$.marca").value("LG"));
    }

}