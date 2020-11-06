package com.jp.dark.models.repository;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.models.entities.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitaRepository extends JpaRepository<Visita, String> {

    boolean existsByCodigo(String codigo);

    Optional<VisitaDTO> findByCodigo(String codigo);
}
