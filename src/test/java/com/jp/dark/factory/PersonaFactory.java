package com.jp.dark.factory;

import com.jp.dark.dtos.PersonaDTO;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonaFactory {
    public static Persona createValidPersona(){
        return Persona.builder()
                .nome("Levi Manuel da Conceição")
                .cpf("08967854676")
                .telefone("33999065029")
                .nascimento(LocalDate.of(1954,8,22))
                .cidade("Tarumirim")
                .categoria(EnumCategoria.AGRICULTOR_FAMILIAR)
                .permissao(EnumPermissao.CLIENTES)
                .endereco("Av. Rui Barbosa, 186 - Bairro: Centro")
                .cep("39610000")
                .senha("ZmXfaUHMNp")
                .build();
    }
    public static PersonaDTO createValidPersonaDTO(){

        return PersonaDTO.builder()
                .nome("Levi Manuel da Conceição")
                .cpf("08967854676")
                .telefone("33999065029")
                //.nascimento(LocalDate.of(1954,8,22))
                .cidade("Tarumirim")
                .categoria(EnumCategoria.AGRICULTOR_FAMILIAR.toString())
                .permissao(EnumPermissao.CLIENTES.toString())
                .endereco("Av. Rui Barbosa, 186 - Bairro: Centro")
                .cep("39610000")
                .senha("ZmXfaUHMNp")
                .build();
    }

    public static List<Persona> createList5ValidPersona() {
        List<Persona> list = new ArrayList<>();
        list.add(createAnyValidPersona());
        list.add(createOtherValidPersona());
        list.add(createThirdValidPersona());
        list.add(createFourthValidPersona());
        list.add(createFifthValidPersona());
        return list;
    }

    private static Persona createAnyValidPersona() {
        return Persona.builder()
                .nome("Fernando Nicolas Anderson Rodrigues")
                .cpf("94224793636")
                .cep("35140000")
                .cidade("Tarumirim")
                .endereco("Sitio pega bicho")
                .categoria(EnumCategoria.AGRICULTOR_FAMILIAR)
                .nascimento(LocalDate.of(1980,4,12))
                .telefone("33999214522")
                .senha("zzzzzz")
                .permissao(EnumPermissao.CLIENTES)
                .build();
    }
    private static Persona createOtherValidPersona() {
        return Persona.builder()
                .nome("Luís Lucca Rafael Moreira")
                .cpf("77455194625")
                .cep("35140000")
                .cidade("Tarumirim")
                .endereco("Sitio escorrega lá vai um")
                .categoria(EnumCategoria.AGRICULTOR_FAMILIAR)
                .nascimento(LocalDate.of(1980,9, 29))
                .telefone("33988214522")
                .senha("abcdef")
                .permissao(EnumPermissao.CLIENTES)
                .build();
    }
    private static Persona createThirdValidPersona() {
        return Persona.builder()
                .nome("Otávio Hugo Aragão")
                .cpf("41202209661")
                .cep("35140000")
                .cidade("Tarumirim")
                .endereco("Sitio Salmo Vinte e um")
                .categoria(EnumCategoria.AGRICULTOR_FAMILIAR)
                .nascimento(LocalDate.of(1954,4, 2))
                .telefone("31984622359")
                .senha("abcdef")
                .permissao(EnumPermissao.CLIENTES)
                .build();
    }
    private static Persona createFourthValidPersona() {
        return Persona.builder()
                .nome("Gael Vitor Luís Moura")
                .cpf("47086703621")
                .cep("35140000")
                .cidade("Tarumirim")
                .endereco("Fazenda mata quatro")
                .categoria(EnumCategoria.AGRICULTOR_FAMILIAR)
                .nascimento(LocalDate.of(1956,7, 1))
                .telefone("33999875619")
                .senha("abcdef")
                .permissao(EnumPermissao.CLIENTES)
                .build();
    }
    private static Persona createFifthValidPersona() {
        return Persona.builder()
                .nome("Marina Olivia Dias")
                .cpf("55558606681")
                .cep("35140000")
                .cidade("Tarumirim")
                .endereco("Fazenda Rancho Alegre")
                .categoria(EnumCategoria.AGRICULTOR_FAMILIAR)
                .nascimento(LocalDate.of(1972,9, 23))
                .telefone("33999161440")
                .senha("abcdef")
                .permissao(EnumPermissao.CLIENTES)
                .build();
    }
}
