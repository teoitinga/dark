package com.jp.dark.services;

import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.models.entities.Persona;

import javax.validation.constraints.NotEmpty;

public interface ProdutorService {
    ProdutorDTO save(ProdutorDTO produtor);

    ProdutorDTO toProdutorDTO(Persona produtor);

    Persona toPersona(ProdutorDTO produtor);

    boolean cpfExists(@NotEmpty String cpf);
}
