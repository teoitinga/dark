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

    @JsonProperty("servico-prestao")
    @NotEmpty(message = "Deve descrever o serviço prestado")
    private String servicoPrestado;

    @JsonProperty("codigo-servico")
    @NotNull(message =  "Deve informar o código do serviço prestado")
    private String serviceProvidedCode;

    @JsonProperty("ocorrencia")
    private String ocorrencia;

    @JsonProperty("cpf-responsavel")
    @NotNull(message =  "Deve informar o funcionario responsavel")
    private String CpfReponsavel;

    @JsonProperty("valor-servico")
    @NotNull(message = "Você deve informar o valor do Serviço")
    private BigDecimal valor;

    @JsonProperty("servico-prestado")
    private String servicoDsc;

    public CallVO(String servicoPrestado, String serviceProvidedCode, String ocorrencia,  String cpfReponsavel, BigDecimal valor) {
        this.servicoPrestado = servicoPrestado;
        this.serviceProvidedCode = serviceProvidedCode;
        this.ocorrencia = ocorrencia;
        this.CpfReponsavel = cpfReponsavel;
        this.valor = valor;
    }
}
