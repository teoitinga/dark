package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private LocalDate dataProducao;
}
