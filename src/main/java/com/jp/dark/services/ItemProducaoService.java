package com.jp.dark.services;

import com.jp.dark.dtos.ItemProducaoDTO;
import com.jp.dark.models.entities.ItemProducao;

import java.util.List;

public interface ItemProducaoService {
    ItemProducao findByCodigo(String codigo);

    List<ItemProducaoDTO> findByDescricaoContainingIgnoreCase(String descricao);
}
