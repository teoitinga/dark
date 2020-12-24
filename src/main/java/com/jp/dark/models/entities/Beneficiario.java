package com.jp.dark.models.entities;

import com.jp.dark.dtos.ProdutorMinDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Beneficiario extends Auditable{

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @NotNull(message = "Você deve informar o beneficiario.")
    @ManyToOne
    private Persona beneficiario;

    @NotNull(message = "Você deve informar a quantidade.")
    private Integer quantidade;

    @ManyToOne
    private Programa programa;

    private String observacoes;
}
