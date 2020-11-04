package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Visita {
    @Id
    @Column

    private String codigo;

    @NotEmpty(message = "Você deve informar a situação atual.")
    @Column
    private String situacao;

    @NotEmpty(message = "Você deve passar uma orientação para a pessoa atendida!")
    @Column
    private String recomendacao;
}
