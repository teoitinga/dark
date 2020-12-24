package com.jp.dark.services;

import com.jp.dark.dtos.OrigemRendaDTO;

import java.util.List;

public interface OrigemRendaService {
    List<OrigemRendaDTO> findByDescricaoContaining(String org);
}
