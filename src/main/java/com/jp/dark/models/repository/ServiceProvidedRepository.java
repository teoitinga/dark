package com.jp.dark.models.repository;

import com.jp.dark.models.entities.ServiceProvided;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, String> {
    Optional<ServiceProvided> findByCodigo(String codigo);

    boolean existsByCodigo(String serviceProvidedCode);

    List<ServiceProvided> findByReferencyContainingIgnoreCase(String srv);

    @Query(value = "SELECT s FROM ServiceProvided s JOIN s.esloc e where lower(s.referency) like %:srv% AND e.codigo = :codigoEsloc")
    List<ServiceProvided> findServicesByReferencyEsloc(@Param("srv") String srv, int codigoEsloc);
}
