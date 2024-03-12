package com.magazinestore.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CaracteristicaResponse {

    @Schema(description = "Id produto", example = "1")
    private Long id;

    @Schema(description = "Nome do produto", example = "Celular")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Este celular possui 128gb e câmera 4k")
    private String descricao;

}