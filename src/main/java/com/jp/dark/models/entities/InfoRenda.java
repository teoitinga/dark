package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class InfoRenda extends Auditable{

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    @Column(name = "codigo", nullable = false, unique = true)
    private Integer codigo;

    @ManyToOne
    private Visita visita;

    @OneToMany(mappedBy = "infoRenda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producao> producaoAnual;

    @Column(name = "area_explorada")
    private BigDecimal areaExplorada;

    @Column(name = "area_imovel_principal")
    private BigDecimal areaImovelPrincipal;

    @Column(name = "qtd_propriedades")
    private Integer quantidadePropriedades;

    @Column(name = "membros_familia")
    private Integer membrosDaFamilia;
}
