package com.jp.dark.dtos;

import com.jp.dark.models.entities.InfoRenda;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class InfoRendaDTO {

    private String codigoVisita;

    @NotEmpty
    private List<ProducaoDTO> producao;

    private String situacaoAtual;

    private String orientacao;

    private String recomendacao;

    @NotEmpty(message = "Você deve informar a propriedade ou local do atendimento.")
    private String localDoAtendimeno;

    @NotEmpty(message = "Deve haver pelo menos 01(um) produtor inscrito.")
    private List<ProdutorMinDTO> produtores;

    @NotNull(message = "Você deve informar a data da visita.")
    private String dataDaVisita;

    private BigDecimal valorCobrado;

    private boolean createFolder;
}
