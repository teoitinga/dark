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
public class Call {
    @Id
    @Column
    private String codigo;

    @NotEmpty(message = "Você deve informar um serviço.")
    @Column
    private String servico;

    @Column
    private String ocorrencia;

    @Column
    private Visita visita;
}
