package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTO {
    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    private String nome;

    private String referencia;

    private String descricao;

    private LocalDate DataInicio;

    private LocalDate DataFim;
}
