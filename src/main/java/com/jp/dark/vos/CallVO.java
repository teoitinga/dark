package com.jp.dark.vos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonRootName(value = "chamada")
public class CallVO implements Serializable {

    @JsonProperty("servicoprestao")
    @NotEmpty(message = "Deve descrever o serviço prestado")
    private String servicoPrestado;

    @JsonProperty("codigoservico")
    @NotNull(message =  "Deve informar o código do serviço prestado")
    private String serviceProvidedCode;

    @JsonProperty("ocorrencia")
    private String ocorrencia;

    @JsonProperty("cpfresponsavel")
    @NotNull(message =  "Deve informar o funcionario responsavel")
    private String CpfReponsavel;

    @JsonProperty("valorservico")
    @NotNull(message = "Você deve informar o valor do Serviço")
    private BigDecimal valor;

    @JsonProperty("servicoprestado")
    private String servicoDsc;

    @JsonProperty("status")
    private String status;

    public CallVO(String servicoPrestado, String serviceProvidedCode, String ocorrencia,  String cpfReponsavel, BigDecimal valor, String status) {
        this.servicoPrestado = servicoPrestado;
        this.serviceProvidedCode = serviceProvidedCode;
        this.ocorrencia = ocorrencia;
        this.CpfReponsavel = cpfReponsavel;
        this.valor = valor;
        this.status = status;
    }
}
