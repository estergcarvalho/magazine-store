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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
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
        ProdutoRequest produto = ProdutoRequest.builder()
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

        ProdutoResponse guardaRoupaResponse = produtoService.cadastrar(produto);

        assertEquals(1L, guardaRoupaResponse.getId().longValue());
        assertEquals(PRODUTO_GUARDA_ROUPA_NOME, guardaRoupaResponse.getNome());
        assertEquals(PRODUTO_GUARDA_ROUPA_DESCRICAO, guardaRoupaResponse.getDescricao());
        assertEquals(PRODUTO_GUARDA_ROUPA_PRECO, guardaRoupaResponse.getPreco());
        assertEquals(PRODUTO_GUARDA_ROUPA_MARCA, guardaRoupaResponse.getMarca());
    }

    @Test
    @DisplayName("Deve listar produtos")
    public void deveListarProdutos() {
        List<Produto> produtos = new ArrayList<>();

        Produto guardaRoupa = Produto.builder()
            .id(1L)
            .nome(PRODUTO_GUARDA_ROUPA_NOME)
            .descricao(PRODUTO_GUARDA_ROUPA_DESCRICAO)
            .preco(PRODUTO_GUARDA_ROUPA_PRECO)
            .marca(PRODUTO_GUARDA_ROUPA_MARCA)
            .build();
        produtos.add(guardaRoupa);

        Produto televisao = Produto.builder()
            .id(2L)
            .nome(PRODUTO_TELEVISAO_NOME)
            .descricao(PRODUTO_TELEVISAO_DESCRICAO)
            .preco(PRODUTO_TELEVISAO_PRECO)
            .marca(PRODUTO_TELEVISAO_MARCA)
            .build();
        produtos.add(televisao);

        when(produtoRepository.findAll()).thenReturn(produtos);

        List<ProdutoResponse> produtosResponse = produtoService.listar();

        assertEquals(2, produtosResponse.size());

        ProdutoResponse guardaRoupaResponse = produtosResponse.get(0);
        assertEquals(1L, guardaRoupaResponse.getId().longValue());
        assertEquals(PRODUTO_GUARDA_ROUPA_NOME, guardaRoupaResponse.getNome());
        assertEquals(PRODUTO_GUARDA_ROUPA_DESCRICAO, guardaRoupaResponse.getDescricao());
        assertEquals(PRODUTO_GUARDA_ROUPA_MARCA, guardaRoupaResponse.getMarca());
        assertEquals(PRODUTO_GUARDA_ROUPA_PRECO, guardaRoupaResponse.getPreco());

        ProdutoResponse televisaoResponse = produtosResponse.get(1);
        assertEquals(2L, televisaoResponse.getId().longValue());
        assertEquals(PRODUTO_TELEVISAO_NOME, televisaoResponse.getNome());
        assertEquals(PRODUTO_TELEVISAO_DESCRICAO, televisaoResponse.getDescricao());
        assertEquals(PRODUTO_TELEVISAO_MARCA, televisaoResponse.getMarca());
        assertEquals(PRODUTO_TELEVISAO_PRECO, televisaoResponse.getPreco());
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

    @Test
    @DisplayName("Deve buscar produto por texto e retornar sucesso")
    public void deveBuscarProdutoPorTextoERetornarSucesso() throws Exception {
        String nome = "ipaD";
        String descricao = "mIni";

        Produto produto = Produto.builder()
            .nome(nome)
            .descricao(descricao)
            .build();

        when(produtoRepository.findByNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(nome, descricao))
            .thenReturn(List.of(produto));

        List<Produto> produtoEncontrado = produtoService.buscarProdutosPorTexto(nome, descricao);

        Produto produtoRetornado = produtoEncontrado.get(0);

        assertEquals(nome, produtoRetornado.getNome());
        assertEquals(descricao, produtoRetornado.getDescricao());
        assertNotNull(produtoRetornado);

    }

    @Test
    @DisplayName("Deve lancar exception para produto não localizado")
    public void deveLancarExceptionParaProdutoNaoLocalizado(){
        String nome = "nome produto inexistente";
        String descricao = "descricao produto inexistente";

        when(produtoRepository.findByNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(nome, descricao))
            .thenReturn(Collections.emptyList());

        assertThrows(ProdutoNaoEncontradoException.class, () -> produtoService.buscarProdutosPorTexto(nome, descricao));
    }

}