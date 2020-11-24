package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    @NotEmpty(message = "Você deve passar uma orientação para a pessoa atendida!")
    @Column
    private String recomendacao;
}
