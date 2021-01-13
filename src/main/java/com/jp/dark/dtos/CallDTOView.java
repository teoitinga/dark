package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallDTOView {

    private String codigo;

    private LocalDate dataDeConclusao;

    private String nomeDoProdutor;

    private String status;

    private String propriedadeRural;

    private String descricaoDoServico;

    private BigDecimal valor;

    private LocalDate dataPgto;
}
