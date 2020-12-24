package com.jp.dark.services.impls;

import com.jp.dark.dtos.OrigemRendaDTO;
import com.jp.dark.models.entities.OrigemRenda;
import com.jp.dark.models.repository.OrigemRendaRepository;
import com.jp.dark.services.OrigemRendaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrigemRendaServiceImpl implements OrigemRendaService {
    private OrigemRendaRepository repository;

    public OrigemRendaServiceImpl(OrigemRendaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<OrigemRendaDTO> findByDescricaoContaining(String org) {
        List<OrigemRenda> result = this.repository.findByDescricaoContainingIgnoreCase(org);
        List<OrigemRendaDTO> list = result.stream().map(entity-> toOrigemRendaDTO(entity)).collect(Collectors.toList());
        return list;
    }

    private OrigemRendaDTO toOrigemRendaDTO(OrigemRenda renda) {
        return OrigemRendaDTO.builder()
                .codigo(renda.getCodigo())
                .descricao(renda.getDescricao())
                .referencia(renda.getReferencia())
                .build();
    }
}
