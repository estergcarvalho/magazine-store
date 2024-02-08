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

    public List<ProdutoResponse> buscarPorId(Long id) throws ProdutoNaoEncontradoException {
        List<ProdutoResponse> listaDeProdutos = new ArrayList<>();

        produtoRepository.findById(id).ifPresent(produto -> {
            ProdutoResponse produtoResponse = ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .marca(produto.getMarca())
                .build();
            listaDeProdutos.add(produtoResponse);
        });

        if (listaDeProdutos.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        }

        return listaDeProdutos;
    }

}