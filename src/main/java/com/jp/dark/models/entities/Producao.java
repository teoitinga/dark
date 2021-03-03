package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Producao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false, unique = true)
    private Integer codigo;

    private BigDecimal valorUnitario;

    private BigDecimal quantidade;

    private LocalDate dataDaProducao;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "info_renda_codigo")
    private InfoRenda infoRenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_producao_codigo")
    private ItemProducao itemProducao;
}
