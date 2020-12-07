package com.jp.dark.factory;

import com.jp.dark.models.entities.Programa;

import java.time.LocalDate;
import java.util.Optional;

public class ProgramaFactory {
    public static Optional<Programa> createNewProgram() {
        Programa programa = Programa.builder()
                .codigo(5)
                .DataFim(LocalDate.now().plusMonths(5))
                .DataInicio(LocalDate.now().minusMonths(1))
                .descricao("Emenda Parlamenter 2020/01 Deputado Leonardo Monteiro")
                .referencia("Sementes de milho")
                .nome("Programa de distribuição de sementes de feijão")
                .build();
        return Optional.of(programa);
    }
}
