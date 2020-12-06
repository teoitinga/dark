package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class InfoPrice extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @DecimalMin("0")
    @Column(precision = 6, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(columnDefinition = "integer default 1")
    private Integer qtdPorUnid;

    @ManyToOne
    private PricesItem especificacao;

    @ManyToOne
    private Persona produtorInformante;
}
