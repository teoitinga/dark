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
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    private String descricao;

    private String referencia;

    private String textoMDA;

    private BigDecimal fatorConvParaAnual;

    @ManyToOne
    @JoinColumn(name = "origem_renda_codigo")
    private OrigemRenda origem;
}
