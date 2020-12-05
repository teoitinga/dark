package com.jp.dark.factory;

import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.models.entities.Persona;

import java.util.ArrayList;
import java.util.List;

public class ProdutorFactory {

    public static ProdutorDTO createNewProdutorDto() {
        return ProdutorDTO.builder()
                .cpf("34546628692")
                .nome("Fábio Cauã Moreira")
                .build();
    }
    public static ProdutorMinDTO createProdutorMinDto() {
        return ProdutorMinDTO.builder()
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

    public static Persona createValidBryan() {
        return new Persona("98370916651","Bryan Lorenzo da Luz");
    }
    public static Persona createValidMatheus() {
        return new Persona("48719787618","Matheus Iago Rocha");
    }
    public static Persona createValidLara() {
        return new Persona("38602989687","Lara Yasmin Ramos");
    }
    public static Persona createValidRenata() {
        return new Persona("49174436600","Renata Adriana Lara Figueiredo");
    }
    public static Persona createValidLucas() {
        return new Persona("22987550600","Lucas João Almeida");
    }
    public static List<ProdutorMinDTO> createList5ValidProdutors() {

        List<ProdutorMinDTO> produtores = new ArrayList<>();
        produtores.add(new ProdutorMinDTO("98370916651","Bryan Lorenzo da Luz"));
        produtores.add(new ProdutorMinDTO("48719787618","Matheus Iago Rocha"));
        produtores.add(new ProdutorMinDTO("38602989687","Lara Yasmin Ramos"));
        produtores.add(new ProdutorMinDTO("49174436600","Renata Adriana Lara Figueiredo"));
        produtores.add(new ProdutorMinDTO("22987550600","Lucas João Almeida"));

        return produtores;
    }
    public static List<Persona> createList5ValidPersona() {

        List<Persona> produtores = new ArrayList<>();
        produtores.add(new Persona("98370916651","Bryan Lorenzo da Luz"));
        produtores.add(new Persona("48719787618","Matheus Iago Rocha"));
        produtores.add(new Persona("38602989687","Lara Yasmin Ramos"));
        produtores.add(new Persona("49174436600","Renata Adriana Lara Figueiredo"));
        produtores.add(new Persona("22987550600","Lucas João Almeida"));

        return produtores;
    }
}
