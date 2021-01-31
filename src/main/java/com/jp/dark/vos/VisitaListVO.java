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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonRootName(value = "visita")
public class VisitaListVO  implements Serializable {

    @JsonProperty("codigodavisita")
    private String codigo;

    @JsonProperty("datavisita")
    private String dataAtd;

    @JsonProperty("localvisita")
    private String local;

    @JsonProperty("produtoratendido")
    private String produtor;

    @JsonProperty("servicodescricao")
    private String servicoDsc;


    public VisitaListVO(String codigo, LocalDate dataDaVisita, String localDoAtendimento, String produtor, String servicoDsc){
        this.codigo = codigo;
        this.dataAtd = dataDaVisita.toString();
        this.local = localDoAtendimento;
        this.produtor = produtor;
        this.servicoDsc = servicoDsc;
    }
}
