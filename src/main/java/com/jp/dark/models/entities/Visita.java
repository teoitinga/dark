package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Visita extends Auditable{

    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotEmpty(message = "Você deve informar a situação atual.")
    @Column
    private String situacao;

    @Column
    private String recomendacao;

    @OneToMany(mappedBy = "visita", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Call> chamadas;

    @NotEmpty(message = "Você deve informar a propriedade ou local do atendimento.")
    @Column
    private  String localDoAtendimento;

    @Column
    private  String orientacao;

    @ManyToMany
    private List<Persona> produtores;

    private LocalDate dataDaVisita;
}
