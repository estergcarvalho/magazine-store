package com.magazinestore.produto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CaracteristicaRequest {

    @Schema(description = "Nome do produto", example = "Televisão")
    @NotBlank(message = "Nome do produto não deve ser nulo ou vazio")
    private String nome;

    @Schema(description = "Descrição do produto", example = "Televisao com resolução 4k")
    @NotBlank(message = "Descrição do produto não deve ser nulo ou vazia")
    private String descricao;

}