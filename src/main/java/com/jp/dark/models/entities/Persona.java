package com.jp.dark.models.entities;

import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Persona {
    @NotEmpty
    @Id
    @CPF
    private String cpf;

    @NotEmpty
    @Length(min = 10)
    private String nome;

    private String telefone;

    private LocalDate nascimento;

    private String endereco;

    private String cidade;

    private String senha;

    private EnumCategoria categoria;

    private EnumPermissao permissao;

}
