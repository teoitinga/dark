package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTO {
    @Id
    @Column(nullable = false, unique = true)
    private String codigo;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String municipio;

    @NotEmpty
    private String referencia;

    @NotEmpty
    private String descricao;

    @NotNull
    private LocalDate DataInicio;

    @NotNull
    private LocalDate DataFim;

    @NotNull
    private List<String> codServicos;
}
