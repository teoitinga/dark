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
public class Persona{
    public Persona(Persona persona) {

        this.cpf = persona.getCpf();
        this.nome = persona.getNome();
        this.categoria = persona.getCategoria();
        this.telefone = persona.getTelefone();
        this.cep = persona.getCep();
        this.cidade = persona.getCidade();
        this.nascimento = persona.getNascimento();
        this.endereco = persona.getEndereco();
        this.permissao = persona.getPermissao();
        this.senha = persona.getSenha();
        this.enabled = persona.getEnabled();
    }

    @Id
    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true, length = 155)
    private String nome;

    private String telefone;

    private LocalDate nascimento;

    private String endereco;

    private String cep;

    private String cidade;

    private String senha;


    @Enumerated(EnumType.STRING)
    private EnumCategoria categoria;

    @Enumerated(EnumType.STRING)
    private EnumPermissao permissao;

    @Column(name = "isenabbled", columnDefinition = "boolean default true")
    private Boolean enabled;

    public Persona(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }
}
