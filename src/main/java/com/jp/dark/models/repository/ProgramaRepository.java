package com.jp.dark.models.repository;

import com.jp.dark.dtos.ProgramaDTO;
import com.jp.dark.models.entities.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
    Optional<Programa> findByCodigo(String codigoDoPrograma);

    @Query("SELECT p FROM Programa p WHERE (p.referencia LIKE %:ref%) AND (p.municipio = :munic) AND (p.DataInicio < now()) AND (p.DataFim > now())")
    List<Programa> findActiveProgramsByReferenciaContainingIgnoreCase(@Param("ref") String ref, @Param("munic") String munic);

    @Query("SELECT p FROM Programa p WHERE (p.referencia LIKE %:ref%) AND (p.municipio = :munic)")
    List<Programa> findAllProgramsByReferenciaContainingIgnoreCase(@Param("ref") String ref, @Param("munic") String munic);

    List<Programa> findByReferenciaContainingIgnoreCase(@Param("ref") String ref);
}
