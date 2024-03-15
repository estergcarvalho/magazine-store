package com.magazinestore.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ProdutoRequest {

    @Schema(description = "Nome do produto", example = "Televisão")
    @NotBlank(message = "Nome do produto não deve ser nulo ou vazio")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Televisao com resolução 4k")
    @NotBlank(message = "Descrição do produto não deve ser nulo ou vazia")
    private String descricao;

    @Schema(description = "Preço do produto", example = "1000.99")
    @NotNull(message = "Preço do produto não deve ser nulo ou vazio")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço do produto deve ser maior que zero")
    private BigDecimal preco;

    @Schema(description = "Marca do produto", example = "LG")
    @NotBlank(message = "Marca do produto não deve ser nulo ou vazia")
    private String marca;

    @Schema(description = "Caracteristicas do produto")
    private List<CaracteristicaRequest> caracteristicas;

}