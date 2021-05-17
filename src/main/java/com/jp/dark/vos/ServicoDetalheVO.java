package com.jp.dark.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName(value = "servicoPrestado")
public class ServicoDetalheVO  implements Serializable {
    @JsonProperty("dataServico")
    private String dataServico;

    @JsonProperty("servicoPrestado")
    private String servicoPrestado;

    @JsonProperty("escritorio")
    private String esloc;

    @JsonProperty("municipio")
    private String municipio;

    @JsonProperty("codChamada")
    private String codChamada;

    @JsonProperty("nomeBeneficiario")
    private String nomeBeneficiario;

    @JsonProperty("nomeResponsavel")
    private String nomeResponsavel;
}
