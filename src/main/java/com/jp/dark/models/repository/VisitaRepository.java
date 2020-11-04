package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitaRepository extends JpaRepository<Visita, String> {

    boolean existsByCodigo(String codigo);
}
