package com.jp.dark.models.repository;

import com.jp.dark.models.entities.ItemProducao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemProducaoRepository extends JpaRepository<ItemProducao, String> {
    ItemProducao findByCodigo(String codigo);

    List<ItemProducao> findByDescricaoContainingIgnoreCase(String descricao);
}
