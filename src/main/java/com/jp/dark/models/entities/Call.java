package com.jp.dark.models.entities;

import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.models.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Call extends Auditable{

    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotEmpty(message = "Você deve informar um serviço.")
    @Column
    private String servico;

    @ManyToOne
    private ServiceProvided serviceProvided;

    @Column
    private String ocorrencia;

    @ManyToOne
    private Visita visita;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Persona> produtores;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    @Column
    private BigDecimal value;

    @Column
    private LocalDate forecast;
}
