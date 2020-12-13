package com.jp.dark.services;

import com.jp.dark.dtos.PersonaDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.dtos.UserDTO;
import com.jp.dark.models.entities.Persona;

import java.util.List;

public interface PersonaService {
    List<ProdutorMinDTO> toListProdutorMinDTO(List<Persona> produtores);

    ProdutorMinDTO toProdutorMinDTO(Persona persona);

    List<Persona> toListPersona(List<ProdutorMinDTO> produtores);

    Persona toPersona(ProdutorMinDTO dto);

    ProdutorDTO save(ProdutorDTO dto);

    ProdutorDTO toProdutorDTO(Persona dto);

    Persona toPersona(ProdutorDTO dto);

    PersonaDTO update(String cpf, PersonaDTO dto);

    boolean cpfIsValid(String cpf);

    boolean personaExists(String cpf);

    List<Persona> toPersona(List<ProdutorMinDTO> produtores);

    List<ProdutorMinDTO> toProdutorMinDTO(List<Persona> produtores);

    Persona save(ProdutorMinDTO produtor);

    Persona findByCpf(String cpfReponsavel);

    UserDTO save(UserDTO dto);

    UserDTO toUserDTO(Persona user);

    Persona toPersona(UserDTO dto);

    UserDTO getDetailsUser(String login);
}
