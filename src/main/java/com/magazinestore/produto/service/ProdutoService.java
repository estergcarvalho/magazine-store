package com.magazinestore.produto.service;

import com.magazinestore.produto.dto.ProdutoRequest;
import com.magazinestore.produto.dto.ProdutoResponse;
import com.magazinestore.produto.exception.ProdutoNaoEncontradoException;
import com.magazinestore.produto.model.Produto;
import com.magazinestore.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoResponse cadastrar(ProdutoRequest produtoRequest) {
        Produto produto = Produto.builder()
            .nome(produtoRequest.getNome())
            .descricao(produtoRequest.getDescricao())
            .preco(produtoRequest.getPreco())
            .marca(produtoRequest.getMarca())
            .build();

        Produto produtoSalvo = produtoRepository.save(produto);

        return ProdutoResponse.builder()
            .id(produtoSalvo.getId())
            .nome(produtoSalvo.getNome())
            .descricao(produtoSalvo.getDescricao())
            .preco(produtoSalvo.getPreco())
            .marca(produtoSalvo.getMarca())
            .build();
    }

    public List<ProdutoResponse> listar() {
        List<Produto> produtos = produtoRepository.findAll();

        List<ProdutoResponse> produtosResponse = new ArrayList<>();

        produtos.forEach(produto -> {
            ProdutoResponse produtoResponse = ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .marca(produto.getMarca())
                .preco(produto.getPreco())
                .build();

            produtosResponse.add(produtoResponse);
        });

        return produtosResponse;

    }

    public ProdutoResponse buscarPorId(Long id) throws ProdutoNaoEncontradoException {
        Optional<Produto> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        }

        Produto produto = produtoOptional.get();

        return ProdutoResponse.builder()
            .id(produto.getId())
            .nome(produto.getNome())
            .descricao(produto.getDescricao())
            .preco(produto.getPreco())
            .marca(produto.getMarca())
            .build();
    }

    public List<Produto> buscarProdutosPorTexto(String nome, String descricao) {
        List<Produto> produtos = produtoRepository
            .findByNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(nome, descricao);

        if (produtos.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        }

        return produtos;
    }

}