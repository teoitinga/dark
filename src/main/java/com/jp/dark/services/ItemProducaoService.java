package com.jp.dark.services;

import com.jp.dark.models.entities.ItemProducao;

public interface ItemProducaoService {
    ItemProducao findByCodigo(String codigo);
}
