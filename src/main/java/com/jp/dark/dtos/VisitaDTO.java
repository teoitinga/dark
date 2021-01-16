package com.jp.dark.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitaDTO {

    private String codigoVisita;

    private String situacaoAtual;

    private String orientacao;

    private String recomendacao;

    private String municipio;

    @NotEmpty(message = "Você deve registrar pelo menos uma chamada!")
    private List<CallDTOPost> chamadas;

    @NotEmpty(message = "Você deve informar a propriedade ou local do atendimento.")
    private String localDoAtendimeno;

    @NotEmpty(message = "Deve haver pelo menos 01(um) produtor inscrito.")
    private List<ProdutorMinDTO> produtores;

    @NotNull(message = "Você deve informar a data da visita.")
    private String dataDaVisita;

    private boolean createFolder;

}
