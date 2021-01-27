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

    @JsonProperty("codigo-da-visita")
    private String codigo;

    @JsonProperty("data-visita")
    private String dataAtd;

    @JsonProperty("local-visita")
    private String local;

    @JsonProperty("produtor-atendido")
    private String produtor;

    @JsonProperty("servico-descricao")
    private String servicoDsc;


    public VisitaListVO(String codigo, LocalDate dataDaVisita, String localDoAtendimento, String produtor, String servicoDsc){
        this.codigo = codigo;
        this.dataAtd = dataDaVisita.toString();
        this.local = localDoAtendimento;
        this.produtor = produtor;
        this.servicoDsc = servicoDsc;
    }
}
