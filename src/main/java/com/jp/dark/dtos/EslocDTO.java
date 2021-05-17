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
public class EslocDTO {

    private int codigo;
    @NotNull(message =  "Deve informar a descrição do esloc")
    private String descricao;

    @NotNull(message =  "Deve informar a referência do esloc")
    private String referency;

    @NotNull(message =  "Deve informar o endereço do esloc")
    private String endereco;

    @NotNull(message =  "Deve informar o município do esloc")
    private String municipio;

    private String fone;

    private String zap;

    private String email;

}
