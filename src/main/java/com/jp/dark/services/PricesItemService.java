package com.jp.dark.services;

import com.jp.dark.models.entities.PricesItem;
import org.springframework.stereotype.Service;


public interface PricesItemService {
    PricesItem findById(String cod);
}
