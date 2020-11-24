package com.jp.dark.factory;

import com.jp.dark.dtos.PersonaDTO;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;

import java.time.LocalDate;

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
}
