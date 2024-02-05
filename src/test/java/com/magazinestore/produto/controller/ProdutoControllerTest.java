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

    @Test
    @DisplayName("Deve cadastrar um produto com sucesso")
    public void deveCadastrarProduto() throws Exception {
        ProdutoRequest produtoRequest = ProdutoRequest.builder()
            .nome("Smart TV 55” UHD 4K LED LG")
            .descricao("Ela possui resolução UHD 4K com tecnologia LED")
            .preco(new BigDecimal("2599.0"))
            .marca("LG")
            .build();

        ProdutoResponse produtoResponse = ProdutoResponse.builder()
            .id(1L)
            .nome("Smart TV 55” UHD 4K LED LG")
            .descricao("Ela possui resolução UHD 4K com tecnologia LED")
            .preco(new BigDecimal("2599.0"))
            .marca("LG")
            .build();

        when(produtoService.cadastrar(any(ProdutoRequest.class))).thenReturn(produtoResponse);
        when(produtoRepository.save(any())).thenReturn(produtoResponse);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.nome").value("Smart TV 55” UHD 4K LED LG"))
            .andExpect(jsonPath("$.descricao").value("Ela possui resolução UHD 4K com tecnologia LED"))
            .andExpect(jsonPath("$.preco").value("2599.0"))
            .andExpect(jsonPath("$.marca").value("LG"));
    }

}