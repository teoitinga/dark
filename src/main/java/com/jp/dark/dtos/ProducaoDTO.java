package com.jp.dark.dtos;

import com.jp.dark.models.entities.ItemProducao;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class ProducaoDTO implements Serializable {

    private Integer codigo;

    private BigDecimal valorUnitario;

    private BigDecimal quantidade;

    @NotEmpty(message = "Você deve descrever a fonte de renda.")
    private String descricao;

    private String codItemProducao;

    private String dataProducao;

}
