package com.jp.dark.models.entities;

import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Persona implements UserDetails, Serializable {
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

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name="persona_escritorio",
            joinColumns={
                    @JoinColumn(name="persona_cpf", referencedColumnName="cpf")},
            inverseJoinColumns={
                    @JoinColumn(name="escritorio_codigo", referencedColumnName="codigo")})
    private Escritorio esloc;

    public Persona(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public Persona(
            String cpf,
            String nome,
            String telefone,
            LocalDate nascimento,
            String endereco,
            String cep,
            String cidade,
            String senha,
            EnumCategoria categoria,
            EnumPermissao permissao,
            boolean enabled
            ) {
        this.cpf = cpf;
        this.nome = nome;
        this.categoria = categoria;
        this.telefone = telefone;
        this.nascimento = nascimento;
        this.endereco = endereco;
        this.cidade = cidade;
        this.cep = cep;
        this.permissao = permissao;
        this.senha = senha;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(this.getPermissao().toString()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Persona persona = (Persona) o;

        return cpf.equals(persona.cpf);
    }

    @Override
    public int hashCode() {
        return cpf.hashCode();
    }
}
