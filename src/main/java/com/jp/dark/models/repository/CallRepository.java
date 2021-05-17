package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.enums.EnumStatus;
import com.jp.dark.vos.ServicosPrestadosVO;
import com.jp.dark.vos.VisitaVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CallRepository extends JpaRepository<Call, String> {

    @Query(value = "select c from Call c where (c.status = 'INICIADA') AND (c.responsavel = :responsavel)")
    Page<Call> findCallsEmAbertoPorResponsavel(Persona responsavel, Pageable pageRequest);

    Page<Call> findByResponsavel(Persona responsavel, Pageable pageRequest);
    @Query(value = "select COUNT(c) from Call c where (c.status = 'INICIADA') AND (c.responsavel = :responsavel)")
    Integer countCalls(@Param("responsavel")Persona responsavel);

    @Query(value = "SELECT c FROM Call c")
    Page<Call> findAllCalls(Pageable pageRequest);

    @Query(value = "select c from Call c where (c.status = 'INICIADA') AND (c.responsavel = :responsavel)")
    List<Call> findAllCallsUser(Persona responsavel, Pageable pageRequest);

    @Query(value = "SELECT c.created, c.servico, p.nome, p.cpf, v.localDoAtendimento, v.municipio FROM Call c join c.visita v join v.produtores p where c.created BETWEEN :inicio AND :fim")
    Page<Object[]> findCallsPorPeriodo(LocalDateTime inicio, LocalDateTime fim, Pageable pageRequest);

    @Query(value = "SELECT s.referency, v.dataDaVisita, e.referency, e.municipio, c.codigo, p.nome, r.nome " +
            "FROM Visita v " +
            "INNER JOIN v.chamadas c " +
            "LEFT JOIN v.produtores p " +
            "INNER JOIN c.serviceProvided s " +
            "RIGHT JOIN s.esloc e " +
            "INNER JOIN c.responsavel r " +
            "WHERE " +
            "(c.created BETWEEN :inicio AND :fim) " +
            "AND (c.status = 'FINALIZADA') " +
            "AND (e.codigo = :codigoEsloc)"
    )
    List<Object[]> findCallsReportPorPeriodo(LocalDateTime inicio, LocalDateTime fim, int codigoEsloc);

    @Query(value = "SELECT v.codigo, v.localDoAtendimento, v.created, c.codigo, c.created, c.servico, p.nome, p.cpf, v.municipio, c.responsavel.nome, c.status, c.valor FROM Call c join c.visita v join v.produtores p where c.created BETWEEN :inicio AND :fim ORDER BY v.created DESC")
    Page<Object[]> findAllCallManager(LocalDateTime inicio, LocalDateTime fim, Pageable pageRequest);

}