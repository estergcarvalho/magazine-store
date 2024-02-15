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

    private static final String PRODUTO_GUARDA_ROUPA_NOME = "Guarda-Roupa de Madeira Maciça";
    private static final String PRODUTO_GUARDA_ROUPA_DESCRICAO = "Guarda-roupa espaçoso com acabamento em madeira";
    private static final BigDecimal PRODUTO_GUARDA_ROUPA_PRECO = new BigDecimal("1299.99");
    private static final String PRODUTO_GUARDA_ROUPA_MARCA = "Silvia Design";

    private static final String PRODUTO_TELEVISAO_NOME = "Smart TV 55” UHD 4K LED LG";
    private static final String PRODUTO_TELEVISAO_DESCRICAO = "Ela possui resolução UHD 4K com tecnologia LED";
    private static final BigDecimal PRODUTO_TELEVISAO_PRECO = new BigDecimal("2599.0");
    private static final String PRODUTO_TELEVISAO_MARCA = "LG";

    @Test
    @DisplayName("Deve cadastrar um produto")
    public void deveCadastrarProduto() throws Exception {
        Long id = 1L;

        ProdutoRequest televisaoRequest = ProdutoRequest.builder()
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        ProdutoResponse televisao = ProdutoResponse.builder()
            .id(id)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        when(produtoRepository.save(any())).thenReturn(televisao);
        when(produtoService.cadastrar(any(ProdutoRequest.class))).thenReturn(televisao);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(televisaoRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.nome").value(PRODUTO_TELEVISAO_NOME))
            .andExpect(jsonPath("$.descricao").value(PRODUTO_TELEVISAO_DESCRICAO))
            .andExpect(jsonPath("$.preco").value(PRODUTO_TELEVISAO_PRECO))
            .andExpect(jsonPath("$.marca").value(PRODUTO_TELEVISAO_MARCA));
    }

    @Test
    @DisplayName("Deve buscar produto por id")
    public void deveBuscarProdutoPorId() throws Exception {
        Long idExistente = 5L;

        ProdutoResponse televisao = ProdutoResponse.builder()
            .id(idExistente)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        Produto produto = Produto.builder()
                .id(idExistente)
                .nome(PRODUTO_TELEVISAO_NOME)
                .descricao(PRODUTO_TELEVISAO_DESCRICAO)
                .preco(PRODUTO_TELEVISAO_PRECO)
                .marca(PRODUTO_TELEVISAO_MARCA)
                .build();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto));
        when(produtoService.buscarPorId(anyLong())).thenReturn(televisao);

        mockMvc.perform(get("/produtos/{id}", idExistente)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(idExistente))
            .andExpect(jsonPath("$.nome").value(PRODUTO_TELEVISAO_NOME))
            .andExpect(jsonPath("$.descricao").value(PRODUTO_TELEVISAO_DESCRICAO))
            .andExpect(jsonPath("$.preco").value(PRODUTO_TELEVISAO_PRECO))
            .andExpect(jsonPath("$.marca").value(PRODUTO_TELEVISAO_MARCA));
    }

    @Test
    @DisplayName("Deve lançar exception para produto não encontrado")
    public void deveLancarExceptionProdutoNaoEncontrado() throws Exception {
        Long idNaoExistente = null;

        when(produtoRepository.findById(idNaoExistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/produtos/{id}", idNaoExistente)
                .contentType("application/json"))
            .andExpect(status().isNotFound());
    }

}