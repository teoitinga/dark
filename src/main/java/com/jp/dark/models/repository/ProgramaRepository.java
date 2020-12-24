package com.jp.dark.models.repository;

import com.jp.dark.dtos.ProgramaDTO;
import com.jp.dark.models.entities.Programa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
    Optional<Programa> findByCodigo(String codigoDoPrograma);

    List<Programa> findByReferenciaContainingIgnoreCase(String referencia);
}
