package com.jp.dark.factory;

import com.jp.dark.dtos.ProducaoDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProducaoFactory {

    public static List<ProducaoDTO> createListItemProducao() {
        List<ProducaoDTO> list = new ArrayList<>();
        list.add(createAnyProducao());
        list.add(createOhterProducao());
        return list;
    }

    private static ProducaoDTO createAnyProducao() {
        return ProducaoDTO.builder()
                .codItemProducao("1")
                .dataProducao("2019/12/25")
                .quantidade(BigDecimal.valueOf(70))
                .valorUnitario(BigDecimal.valueOf(280))
                .build();
    }
    private static ProducaoDTO createOhterProducao() {
        return ProducaoDTO.builder()
                .codItemProducao("2")
                .dataProducao("2019/05/08")
                .quantidade(BigDecimal.valueOf(700))
                .valorUnitario(BigDecimal.valueOf(1.22))
                .build();
    }
}
