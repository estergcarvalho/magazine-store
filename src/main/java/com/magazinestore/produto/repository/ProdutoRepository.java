package com.magazinestore.produto.repository;

import com.magazinestore.produto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeIgnoreCaseContainingOrDescricaoIgnoreCaseContaining(String nome, String descricao);

}