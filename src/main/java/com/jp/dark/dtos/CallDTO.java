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
public class CallDTO {

    private String codigo;

    @NotEmpty
    private String servico;

    @NotEmpty
    private String ocorrencia;

    @NotEmpty
    private String codigoVisita;

}
