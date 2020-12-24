package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumPermissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, String> {

    boolean existsByCpf(String cpf);

    Optional<Persona> findByCpf(String cpf);

    List<Persona> findByNomeContainingIgnoreCaseAndPermissao(String name, EnumPermissao permissao);

    List<Persona> findByNomeContainingIgnoreCaseAndPermissaoNot(String name, EnumPermissao permissao);
}
