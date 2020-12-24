package com.jp.dark.models.repository;

import com.jp.dark.models.entities.OrigemRenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrigemRendaRepository extends JpaRepository<OrigemRenda, String> {
    List<OrigemRenda> findByDescricaoContainingIgnoreCase(String org);

}
