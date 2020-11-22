package com.jp.dark.factory;

import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.models.entities.Persona;

public class ProdutorFactory {

    public static ProdutorDTO createNewProdutorDto() {
        return ProdutorDTO.builder()
                .cpf("34546628692")
                .nome("Fábio Cauã Moreira")
                .build();
    }
    public static Persona createNewPersona() {
        return Persona
                .builder()
                .cpf("34546628692")
                .nome("Fábio Cauã Moreira")
                .build();
    }

    public static ProdutorDTO createAnyProdutorDto() {
        return ProdutorDTO.builder()
                .cpf("37123700678")
                .nome("Marcela Rebeca Moreira")
                .build();
    }
    public static ProdutorDTO createAnyProdutorWithInvalidCPFDto() {
        return ProdutorDTO.builder()
                .cpf("37123700677")
                .nome("Heitor Kauê Kaique da Rosa")
                .build();
    }
    public static ProdutorDTO createAnyProdutorWithInvalidNameDto() {
        return ProdutorDTO.builder()
                .cpf("37123700677")
                .nome("Heitor Kauê Kaique da Rosa")
                .build();
    }
}
