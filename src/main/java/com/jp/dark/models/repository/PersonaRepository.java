package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, String> {

    boolean existsByCpf(String cpf);

    Optional<Persona> findByCpf(String cpf);
}
