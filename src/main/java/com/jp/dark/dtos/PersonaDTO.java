package com.jp.dark.dtos;

import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonaDTO {

    private String cpf;

    private String nome;

    private String telefone;

    private LocalDate nascimento;

    private String endereco;

    private String cep;

    private String cidade;

    private String senha;

    private String categoria;

    private String permissao;
}
