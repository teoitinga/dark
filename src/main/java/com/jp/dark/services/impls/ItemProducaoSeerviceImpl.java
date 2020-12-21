package com.jp.dark.services.impls;

import com.jp.dark.models.entities.ItemProducao;
import com.jp.dark.models.repository.ItemProducaoRepository;
import com.jp.dark.services.ItemProducaoService;
import org.springframework.stereotype.Service;

@Service
public class ItemProducaoSeerviceImpl implements ItemProducaoService {

    private ItemProducaoRepository repository;

    public ItemProducaoSeerviceImpl(ItemProducaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ItemProducao findByCodigo(String codigo) {
        return this.repository.findByCodigo(codigo);
    }
}
