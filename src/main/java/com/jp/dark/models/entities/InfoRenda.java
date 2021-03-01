package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class InfoRenda extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codigo;

    @ManyToOne
    private Visita visita;

    private BigDecimal valorUnitario;

    private BigDecimal quantidade;

    @ManyToOne
    private ItemProducao itemProducao;

    @ManyToOne
    private Producao producaoAnual;

    private LocalDate dataProducao;

    @Column(name = "descricao")
    private String descricao;

    private boolean createFolder;

    @Column(name = "area_explorada")
    private BigDecimal areaExplorada;

    @Column(name = "area_imovel_principal")
    private BigDecimal areaImovelPrincipal;

    @Column(name = "qtd_propriedades")
    private Integer quantidadePropriedades;

    @Column(name = "membros_familia")
    private Integer membrosDaFamilia;
}
