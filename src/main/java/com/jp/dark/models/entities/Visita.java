package com.jp.dark.models.entities;

import lombok.*;

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

    @Column(length = 65535, columnDefinition = "TEXT")
    private String situacao;

    @Column(length = 65535, columnDefinition = "TEXT")
    private String recomendacao;

    @ToString.Exclude
    @OneToMany(mappedBy = "visita", fetch = FetchType.LAZY)
    private List<Call> chamadas;

    @NotEmpty(message = "Você deve informar a propriedade ou local do atendimento.")
    @Column
    private  String localDoAtendimento;

    @Column(length = 65535, columnDefinition = "TEXT")
    private  String orientacao;

    @Column
    private  String municipio;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Persona> produtores;

    private LocalDate dataDaVisita;
}
