package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CallRepository extends JpaRepository<Call, String> {

    @Query("select c from Call c where (c.status = 'INICIADA') AND (c.responsavel = :responsavel)")
    Page<Call> findCallsEmAbertoPorResponsavel(Persona responsavel, Pageable pageRequest);

    Page<Call> findByResponsavel(Persona responsavel, Pageable pageRequest);
    @Query("select COUNT(c) from Call c where (c.status = 'INICIADA') AND (c.responsavel = :responsavel)")
    Integer countCalls(@Param("responsavel")Persona responsavel);
}
