package com.jp.dark.services;

import com.jp.dark.dtos.PersonaDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.models.entities.Persona;

import javax.validation.constraints.NotEmpty;

public interface ProdutorService {
    ProdutorDTO save(ProdutorDTO produtor);

    ProdutorDTO toProdutorDTO(Persona produtor);

    boolean cpfExists(@NotEmpty String cpf);

    PersonaDTO update(String cpf, PersonaDTO dto);

    Persona findByCpf(String cpf);
}
