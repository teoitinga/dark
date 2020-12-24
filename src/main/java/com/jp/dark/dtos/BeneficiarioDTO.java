package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiarioDTO {

    private String id;

    @NotNull(message = "Você deve informar o beneficiario.")
    private ProdutorMinDTO beneficiario;

    @NotNull(message = "Você deve informar a quantidade.")
    private Integer quantidade;

    @NotNull(message = "Você deve informar o código do programa.")
    private String codigoDoPrograma;

    private String observacoes;

}
