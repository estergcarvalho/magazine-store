package com.magazinestore.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CaracteristicaRequest {

    @Schema(description = "Nome da caracteristica", example = "Cor")
    @NotBlank(message = "Nome da caracteristica do produto não deve ser nulo ou vazio")
    private String nome;

    @Schema(description = "Descrição da caracteristica", example = "Rosa")
    @NotBlank(message = "Descrição da caracteristica do produto não deve ser nulo ou vazia")
    private String descricao;

}