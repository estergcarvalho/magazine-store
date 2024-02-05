package com.magazinestore.produto.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ProdutoRequest {

    @NotBlank(message = "Nome do produto não estar vazio")
    private String nome;

    @NotBlank(message = "Descrição do produto não estar vazio")
    private String descricao;

    @NotNull(message = "Preço do produto não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço de produto deve ser maior que zero")
    private BigDecimal preco;

    @NotBlank(message = "Marca do produto não estar vazio")
    private String marca;

}