package com.jp.dark.factory;

import com.jp.dark.models.entities.OrigemRenda;

public class OrigemRendaFactory {
    public static OrigemRenda queijoAgroindustria() {
        return OrigemRenda.builder()
                .codigo("AGF")
                .descricao("Agroindustria familiar")
                .referencia("Agroindustria")
                .build();
    }
}
