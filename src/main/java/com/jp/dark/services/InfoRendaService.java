package com.jp.dark.services;

import com.jp.dark.dtos.InfoRendaDTO;
import com.jp.dark.dtos.ItemProducaoDTO;
import com.jp.dark.dtos.OrigemRendaDTO;
import com.jp.dark.dtos.ProducaoDTO;
import com.jp.dark.models.entities.InfoRenda;
import com.jp.dark.models.entities.ItemProducao;
import com.jp.dark.models.entities.Producao;
import com.jp.dark.models.entities.Visita;

import java.util.List;

public interface InfoRendaService {
    InfoRendaDTO save(InfoRendaDTO dto);

    Producao toProducao(ProducaoDTO prd);

    InfoRenda register(ProducaoDTO prd, Visita vs);

    List<ItemProducaoDTO> findByDescricaoContaining(String descricao);

    List<OrigemRendaDTO> findOrigemByDescricaoContaining(String org);
}
