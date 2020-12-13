package com.jp.dark.factory;

import com.jp.dark.models.entities.ItemProducao;

import java.math.BigDecimal;

public class ItemProducaoFactory {
    public static ItemProducao anyItemProducao() {
        return ItemProducao.builder()
                .codigo("QJ")
                .descricao("Produção diária de queijo frescal")
                .fatorConvParaAnual(BigDecimal.valueOf(300))
                .referencia("Produção de queijo frescal")
                .origem(OrigemRendaFactory.queijoAgroindustria())
                .build();

    }
}
