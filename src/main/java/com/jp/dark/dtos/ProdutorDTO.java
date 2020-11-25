package com.jp.dark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutorDTO {

    @NotEmpty
    private String cpf;

    @NotEmpty
    private String nome;

    private String telefone;

    private LocalDate nascimento;

    private String categoria;

    private String endereco;

    private String cidade;

    private String cep;

}
