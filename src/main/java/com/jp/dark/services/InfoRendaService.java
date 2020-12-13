package com.jp.dark.services;

import com.jp.dark.dtos.InfoRendaDTO;
import com.jp.dark.dtos.ProducaoDTO;
import com.jp.dark.models.entities.InfoRenda;
import com.jp.dark.models.entities.Visita;

public interface InfoRendaService {
    InfoRendaDTO save(InfoRendaDTO dto);

    ProducaoDTO toProducaoDTO(InfoRenda item);

    InfoRenda register(ProducaoDTO prd, Visita vs);
}
