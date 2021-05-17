package com.jp.dark.vos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName(value = "servicos")
public class ServicosReportVO implements Serializable {
    @JsonProperty("relatorio")
    private String relatorio;

    @JsonProperty("dataInicial")
    private String dataInicial;

    @JsonProperty("dataFinal")
    private String dataFinal;

    @JsonProperty("servico")
    private List<ServicoDetalheVO> servico;
}
