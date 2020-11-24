package com.jp.dark.models.entities;

import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Persona extends Auditable{

    @Id
    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true, length = 155)
    private String nome;

    private String telefone;

    private LocalDate nascimento;

    private String endereco;

    private String cidade;

    private String senha;

    private EnumCategoria categoria;

    private EnumPermissao permissao;

    public Persona(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }
}
