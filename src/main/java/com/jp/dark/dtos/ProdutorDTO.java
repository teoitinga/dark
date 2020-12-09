package com.jp.dark.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/YYYY")
    private LocalDate nascimento;

    private String categoria;

    private String endereco;

    private String cidade;

    private String cep;

}
