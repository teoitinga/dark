package com.jp.dark.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProducaoDTO {

    private BigDecimal valorUnitario;
    private BigDecimal quantidade;
    private String codItemProducao;
    private String dataProducao;

}
