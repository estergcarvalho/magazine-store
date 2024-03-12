package com.magazinestore.produto.service;

import com.magazinestore.produto.dto.CaracteristicaResponse;
import com.magazinestore.produto.dto.ProdutoRequest;
import com.magazinestore.produto.dto.ProdutoResponse;
import com.magazinestore.produto.exception.ProdutoNaoEncontradoException;
import com.magazinestore.produto.model.Caracteristica;
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

        List<Caracteristica> caracteristicasDoProduto = new ArrayList<>();

        produtoRequest.getCaracteristicas()
            .forEach(caracteristicaRequest -> {
                Caracteristica novaCaracteristica = new Caracteristica();
                novaCaracteristica.setNome(caracteristicaRequest.getNome());
                novaCaracteristica.setDescricao(caracteristicaRequest.getDescricao());
                novaCaracteristica.setProduto(produto);
                caracteristicasDoProduto.add(novaCaracteristica);
            });

        produto.setCaracteristica(caracteristicasDoProduto);

        Produto produtoSalvo = produtoRepository.save(produto);

        List<CaracteristicaResponse> caracteristicasResponse = new ArrayList<>();

        produtoSalvo.getCaracteristica().forEach(caracteristica -> {
            CaracteristicaResponse caracteristicaResponse = new CaracteristicaResponse(
                caracteristica.getId(),
                caracteristica.getNome(),
                caracteristica.getDescricao()
            );
            caracteristicasResponse.add(caracteristicaResponse);
        });

        return ProdutoResponse.builder()
            .id(produtoSalvo.getId())
            .nome(produtoSalvo.getNome())
            .descricao(produtoSalvo.getDescricao())
            .preco(produtoSalvo.getPreco())
            .marca(produtoSalvo.getMarca())
            .caracteristicas(caracteristicasResponse)
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

    public ProdutoResponse atualizarProduto(Long produtoId, ProdutoRequest produtoRequest) {
        Optional<Produto> produto = produtoRepository.findById(produtoId);

        if (produto.isEmpty()) {
            throw new ProdutoNaoEncontradoException();
        }

        Produto produtoExistente = produto.get();

        List<Caracteristica> caracteristicas = new ArrayList<>();

        if (produtoRequest.getCaracteristicas() != null) {

            produtoRequest.getCaracteristicas()

                .forEach(addCaracteristica -> {
                Caracteristica caracteristica = Caracteristica
                    .builder()
                    .nome(addCaracteristica.getNome())
                    .descricao(addCaracteristica.getDescricao())
                    .produto(produtoExistente)
                    .build();
                caracteristicas.add(caracteristica);
            });
        }

        Produto produtoAtualizado = Produto.builder()
            .id(produtoExistente.getId())
            .nome(produtoRequest.getNome())
            .descricao(produtoRequest.getDescricao())
            .preco(produtoRequest.getPreco())
            .marca(produtoRequest.getMarca())
            .caracteristica(caracteristicas)
            .build();

        produtoAtualizado = produtoRepository.save(produtoAtualizado);

        return ProdutoResponse.builder()
            .id(produtoAtualizado.getId())
            .nome(produtoAtualizado.getNome())
            .descricao(produtoAtualizado.getDescricao())
            .preco(produtoAtualizado.getPreco())
            .marca(produtoAtualizado.getMarca())
            .build();
    }

}