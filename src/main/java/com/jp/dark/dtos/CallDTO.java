package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallDTO {

    private String codigo;

    @NotEmpty(message = "Deve Descrever o serviço prestado")
    private String servico;

    @NotNull(message =  "Deve informar o código do serviço prestado")
    private String serviceProvidedCode;

    private String ocorrencia;

    private BigDecimal valor;

    @NotNull(message =  "Deve informar o funcionario responsave")
    private String CpfReponsavel;

    private String status;

    private String conclusaoPrevista;

    private String servicoQuitadoEm;

    private String codigoDaVisita;
}
