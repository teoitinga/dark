package com.jp.dark.services;

import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.models.entities.Persona;

import java.util.List;

public interface PersonaService {
    boolean cpfIsValid(String cpf);

    boolean PersonaExists(String cpf);

    List<Persona> toPersona(List<ProdutorMinDTO> produtores);

    Persona toPersona(ProdutorMinDTO produtor);

    ProdutorMinDTO save(ProdutorMinDTO produtor);

    ProdutorMinDTO toProdutorMinDTO(Persona persona);

    List<ProdutorMinDTO> toProdutorMinDTO(List<Persona> produtores);
}
