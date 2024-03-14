package com.magazinestore.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CaracteristicaResponse {

    @Schema(description = "Id caracteristica", example = "1")
    private Long id;

    @Schema(description = "Nome da caracteristica", example = "Cor")
    private String nome;

    @Schema(description = "Descrição da caracteristica", example = "Prata")
    private String descricao;

}