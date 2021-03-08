package com.jp.dark.models.repository;

import com.jp.dark.models.entities.Producao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducaoRepository extends JpaRepository<Producao, Integer> {
}
