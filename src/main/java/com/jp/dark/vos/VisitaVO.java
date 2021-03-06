package com.jp.dark.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.jp.dark.models.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonRootName(value = "visita")
public class VisitaVO implements Serializable {

    @JsonProperty("codvisita")
    private String codigo;

    @JsonProperty("datavisita")
    private String dataAtd;

    @JsonProperty("localvisita")
    private String local;

    @JsonProperty("tecnicoresp")
    private String responsavel;

    @JsonProperty("servicoprestado")
    private String servicoDsc;

    @JsonProperty("statusvisita")
    private String status;

    public VisitaVO(String codigo, String dataDaVisita, String localDoAtendimento, String responsavel, String status, String servicoDsc){
        this.codigo = codigo;
        this.dataAtd = dataDaVisita.toString();
        this.local = localDoAtendimento;
        this.responsavel = responsavel;
        this.status = status.toString();
        this.servicoDsc = servicoDsc;
    }
}
