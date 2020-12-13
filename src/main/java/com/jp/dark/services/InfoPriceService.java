package com.jp.dark.services;

import com.jp.dark.dtos.PricesDTO;
import com.jp.dark.models.entities.InfoPrice;

public interface InfoPriceService {
    PricesDTO save(PricesDTO dto);

    PricesDTO toInfoPriceDTO(InfoPrice infoPrice);

    InfoPrice toInfoPrice(PricesDTO dto);
}
