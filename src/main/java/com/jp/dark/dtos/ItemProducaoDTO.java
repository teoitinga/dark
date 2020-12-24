package com.jp.dark.dtos;

import com.jp.dark.models.entities.OrigemRenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemProducaoDTO {

    private String codigo;

    private String descricao;

    private String referencia;

    private String textoMDA;

    private BigDecimal fatorConvParaAnual;

}
