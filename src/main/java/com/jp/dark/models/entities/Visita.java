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

    @Column(length = 65535, columnDefinition = "TEXT")
    private String situacao;

    @Column(length = 65535, columnDefinition = "TEXT")
    private String recomendacao;

    @OneToMany(mappedBy = "visita")
    private List<Call> chamadas;

    @NotEmpty(message = "Você deve informar a propriedade ou local do atendimento.")
    @Column
    private  String localDoAtendimento;

    @Column(length = 65535, columnDefinition = "TEXT")
    private  String orientacao;

    @Column
    private  String municipio;

    @ManyToMany
    private List<Persona> produtores;

    private LocalDate dataDaVisita;
}
