package com.jp.dark.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Programa {
    @Id
    @Column(nullable = false, unique = true)
    private Integer codigo;

    private String nome;

    private String referencia;

    private String descricao;

    private LocalDate DataInicio;

    private LocalDate DataFim;
}
