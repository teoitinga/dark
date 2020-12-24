package com.jp.dark.models.repository;

import com.jp.dark.models.entities.ServiceProvided;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceProvidedRepository extends JpaRepository<ServiceProvided, String> {
    Optional<ServiceProvided> findByCodigo(String codigo);

    boolean existsByCodigo(String serviceProvidedCode);

    List<ServiceProvided> findByReferencyContainingIgnoreCase(String srv);
}
