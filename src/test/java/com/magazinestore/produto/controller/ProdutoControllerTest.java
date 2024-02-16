package com.magazinestore.produto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magazinestore.produto.dto.ProdutoRequest;
import com.magazinestore.produto.model.Produto;
import com.magazinestore.produto.repository.ProdutoRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
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

        Produto televisao = Produto.builder()
            .id(id)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        when(produtoRepository.save(any())).thenReturn(televisao);

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
    @DisplayName("Deve listar produtos")
    public void deveListarProdutos() throws Exception {
        Long idGuardaRoupa = 1L;
        Long idTelevisao = 2L;

        Produto guardaRoupa = Produto.builder()
            .id(idGuardaRoupa)
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();

        Produto televisao = Produto.builder()
            .id(idTelevisao)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        List<Produto> produtos = Arrays.asList(guardaRoupa, televisao);

        when(produtoRepository.findAll()).thenReturn(produtos);

        mockMvc.perform(get("/produtos")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(idGuardaRoupa))
            .andExpect(jsonPath("$[0].nome").value(PRODUTO_GUARDA_ROUPA_NOME))
            .andExpect(jsonPath("$[0].descricao").value(PRODUTO_GUARDA_ROUPA_DESCRICAO))
            .andExpect(jsonPath("$[0].preco").value(PRODUTO_GUARDA_ROUPA_PRECO))
            .andExpect(jsonPath("$[0].marca").value(PRODUTO_GUARDA_ROUPA_MARCA))
            .andExpect(jsonPath("$[1].id").value(idTelevisao))
            .andExpect(jsonPath("$[1].nome").value(PRODUTO_TELEVISAO_NOME))
            .andExpect(jsonPath("$[1].descricao").value(PRODUTO_TELEVISAO_DESCRICAO))
            .andExpect(jsonPath("$[1].preco").value(PRODUTO_TELEVISAO_PRECO))
            .andExpect(jsonPath("$[1].marca").value(PRODUTO_TELEVISAO_MARCA));
    }

    @Test
    @DisplayName("Deve buscar produto por id")
    public void deveBuscarProdutoPorId() throws Exception {
        Long idTelevisao = 5L;

        Produto televisao = Produto.builder()
            .id(idTelevisao)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(televisao));

        mockMvc.perform(get("/produtos/{id}", idTelevisao)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(idTelevisao))
            .andExpect(jsonPath("$.nome").value(PRODUTO_TELEVISAO_NOME))
            .andExpect(jsonPath("$.descricao").value(PRODUTO_TELEVISAO_DESCRICAO))
            .andExpect(jsonPath("$.preco").value(PRODUTO_TELEVISAO_PRECO))
            .andExpect(jsonPath("$.marca").value(PRODUTO_TELEVISAO_MARCA));
    }

    @Test
    @DisplayName("Deve lançar exception para produto não encontrado")
    public void deveLancarExceptionProdutoNaoEncontrado() throws Exception {
        Long idNaoExistente = 999999L;

        when(produtoRepository.findById(idNaoExistente)).thenReturn(Optional.empty());

        mockMvc.perform(get("/produtos/{id}", idNaoExistente)
                .contentType("application/json"))
            .andExpect(status().isNotFound());
    }

}