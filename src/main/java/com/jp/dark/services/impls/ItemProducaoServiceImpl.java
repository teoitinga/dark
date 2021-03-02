package com.jp.dark.services.impls;

import com.jp.dark.dtos.ItemProducaoDTO;
import com.jp.dark.models.entities.ItemProducao;
import com.jp.dark.models.entities.OrigemRenda;
import com.jp.dark.models.enums.EnumSimNao;
import com.jp.dark.models.repository.ItemProducaoRepository;
import com.jp.dark.services.ItemProducaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemProducaoServiceImpl implements ItemProducaoService {

    private ItemProducaoRepository repository;

    public ItemProducaoServiceImpl(ItemProducaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemProducao findByCodigo(String codigo) {
        return this.repository.findByCodigo(codigo);
    }

    @Override
    public List<ItemProducaoDTO> findByDescricaoContainingIgnoreCase(String descricao) {
        List<ItemProducao> result = repository.findByDescricaoContainingIgnoreCase(descricao);
        List<ItemProducaoDTO> list = result.stream().map(entity -> toItemProducaoDTO(entity)).collect(Collectors.toList());
        return list;
    }

    private ItemProducaoDTO toItemProducaoDTO(ItemProducao item) {
        EnumSimNao isAgro = EnumSimNao.NAO;
        OrigemRenda origemRenda = item.getOrigem();

        try{
        isAgro = item.getOrigem().getIsAgro();

        }catch (NullPointerException exc){
            isAgro = EnumSimNao.NAO;
        }

        log.info(origemRenda.getIsAgro().toString());
        log.info(origemRenda.getDescricao());
        return ItemProducaoDTO.builder()
                .codigo(item.getCodigo().toString())
                .descricao(item.getDescricao())
                .fatorConvParaAnual(item.getFatorConvParaAnual())
                .referencia(item.getReferencia())
                .textoMDA(item.getTextoMDA())
                .producaoAgropecuaria(isAgro)
                .build();
    }
}
