package com.jp.dark.services.impls;

import com.jp.dark.exceptions.PriceItemNotFoundException;
import com.jp.dark.models.entities.PricesItem;
import com.jp.dark.models.repository.PricesItemRepository;
import com.jp.dark.services.PricesItemService;
import org.springframework.stereotype.Service;

@Service
public class PricesItemServiceImpl implements PricesItemService {

    private PricesItemRepository repository;

    public PricesItemServiceImpl(PricesItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public PricesItem findById(String cod) {
        return this.repository.findById(cod).orElseThrow(()->new PriceItemNotFoundException());
    }
}
