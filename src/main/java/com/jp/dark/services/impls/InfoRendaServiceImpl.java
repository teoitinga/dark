package com.jp.dark.services.impls;

import com.jp.dark.dtos.InfoRendaDTO;
import com.jp.dark.dtos.ProducaoDTO;
import com.jp.dark.models.entities.InfoRenda;
import com.jp.dark.models.entities.Visita;
import org.springframework.stereotype.Service;

@Service
public class InfoRendaServiceImpl implements com.jp.dark.services.InfoRendaService {

    @Override
    public InfoRendaDTO save(InfoRendaDTO dto) {
        return dto;
    }

    @Override
    public ProducaoDTO toProducaoDTO(InfoRenda item) {
        return null;
    }

    @Override
    public InfoRenda register(ProducaoDTO prd, Visita vs) {
        return null;
    }
}
