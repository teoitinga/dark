package com.jp.dark.models.repository;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.vos.VisitaVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VisitaRepository extends JpaRepository<Visita, String> {

    boolean existsByCodigo(String codigo);

    Optional<Visita> findByCodigo(String codigo);

    Page<Visita> findAll(Pageable pageRequest);

//    Page<Visita> findAllVisitaManager(Pageable pageRequest);

}
