package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitaDTO {

    private String codigo;

    @NotEmpty(message = "Você deve informar a situação atual.")
    private String situacao;

    @NotEmpty(message = "Você deve passar uma orientação para a pessoa atendida!")
    private String recomendacao;

}
