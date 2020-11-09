package com.jp.dark.services;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.models.entities.Visita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VisitaService {
    VisitaDTO save(VisitaDTO any);

    String createId(String key);

    VisitaDTO toVisitaDto(Visita entity);

    Visita toVisita(VisitaDTO visita);

    Optional<VisitaDTO> getByCodigo(String codigo);

    VisitaDTO update(VisitaDTO visita);

    void delete(Visita visita);

    Page<VisitaDTO> find(VisitaDTO filter, Pageable pageRequest);
}