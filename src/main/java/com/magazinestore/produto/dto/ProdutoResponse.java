package com.magazinestore.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ProdutoResponse {

    @Schema(description = "Id produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Celular")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Este celular possui 128gb e câmera 4k")
    private String descricao;

    @Schema(description = "Preço do produto", example = "5050.99")
    private BigDecimal preco;

    @Schema(description = "Marca do produto", example = "Apple")
    private String marca;

    @Schema(description = "Caracteristicas do produto", example = "cor, dimensão, material")
    private List<CaracteristicaResponse> caracteristicas;

}