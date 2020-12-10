package com.jp.dark.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class CallDTOPost {

    private String codigo;

    @NotEmpty(message = "Deve descrever o serviço prestado")
    private String servicoPrestado;

    @NotNull(message =  "Deve informar o código do serviço prestado")
    private String serviceProvidedCode;

    private String ocorrencia;

    @NotNull(message =  "Deve informar o funcionario responsave")
    private String CpfReponsavel;

    @NotNull(message = "Você deve informar o valor do Serviço")
    private BigDecimal valor;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String servicoQuitadoEm;

    private String status;

    private String codigoDaVisita;

}
