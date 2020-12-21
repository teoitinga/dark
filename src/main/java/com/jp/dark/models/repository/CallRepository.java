package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CallRepository extends JpaRepository<Call, String> {

    Page<Call> findByResponsavel(Persona responsavel, Pageable pageRequest);
}
