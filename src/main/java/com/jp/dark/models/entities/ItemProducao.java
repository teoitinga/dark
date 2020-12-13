package com.jp.dark.models.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class ItemProducao {

    @Id
    private String codigo;

    private String descricao;

    private String referencia;

    private String textoMDA;

    private BigDecimal fatorConvParaAnual;

    @ManyToOne
    private OrigemRenda origem;
}
