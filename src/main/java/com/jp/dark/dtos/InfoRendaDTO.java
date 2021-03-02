package com.jp.dark.dtos;

import com.jp.dark.models.entities.InfoRenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoRendaDTO {

    private String codigoVisita;

    @NotEmpty
    private List<ProducaoDTO> producao;

    private String situacaoAtual;

    private String orientacao;

    private String recomendacao;
/*
    @NotEmpty(message = "Você deve descrever a fonte de renda.")
    private String descricao;
*/
    @NotEmpty(message = "Você deve informar a propriedade ou local do atendimento.")
    private String localDoAtendimeno;

    @NotEmpty(message = "Deve haver pelo menos 01(um) produtor inscrito.")
    private List<ProdutorMinDTO> produtores;

    @NotNull(message = "Você deve informar a data da visita.")
    private String dataDaVisita;

    private BigDecimal valorCobrado;

    private boolean createFolder;

    @NotNull(message = "Você deve informar a área total explorada.")
    private BigDecimal areaExplorada;

    @NotNull(message = "Você deve informar a área do imóvel principal.")
    private BigDecimal areaImovelPrincipal;

    @NotNull(message = "Você deve informar a quantidade de imóveis explorados.")
    private Integer quantidadePropriedades;

    @NotNull(message = "Você deve informar a quantidade de membros da familia.")
    private Integer membrosDaFamilia;
}
