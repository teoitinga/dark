package com.jp.dark.factory;


import com.jp.dark.dtos.PricesDTO;
import com.jp.dark.models.entities.PricesItem;

import java.math.BigDecimal;

public class PricesFactory {
    public static PricesDTO createPriceBoiMagro() {
        return PricesDTO.builder()
                .cpfProdutorInformante("04459471604")
                .especificacaoCod("1")
                .valorUnitario(BigDecimal.valueOf(350.00))
                .qtdPorUnid(6)
                .build();
    }

    public static PricesDTO createPriceVacaMagra() {
        return PricesDTO.builder()
                .cpfProdutorInformante("04459471604")
                .especificacaoCod("2")
                .valorUnitario(BigDecimal.valueOf(210.25))
                .qtdPorUnid(13)
                .build();
    }

    public static PricesDTO createPriceLitroDeLeite() {
        return PricesDTO.builder()
                .cpfProdutorInformante("04459471604")
                .especificacaoCod("3")
                .valorUnitario(BigDecimal.valueOf(1.58))
                .qtdPorUnid(1)
                .build();

    }

    public static PricesDTO createPriceBoiGordo() {
        return PricesDTO.builder()
                .cpfProdutorInformante("04459471604")
                .especificacaoCod("4")
                .valorUnitario(BigDecimal.valueOf(210.22))
                .qtdPorUnid(16)
                .build();
    }
    public static PricesDTO createPriceNoInfo() {
        return PricesDTO.builder()

                .build();
    }

    public static PricesDTO createPriceNoQtdPorUnid() {
        return PricesDTO.builder()
                .cpfProdutorInformante("04459471604")
                .especificacaoCod("4")
                .valorUnitario(BigDecimal.valueOf(210.22))
                .build();
    }

    public static PricesItem createPriceItem() {
        return PricesItem.builder()
                .detalhes("Boi de 5-8 arrobas")
                .especificação("Boi Magro")
                .unidade("@")
                .id(1)
                .build();

    }
}
