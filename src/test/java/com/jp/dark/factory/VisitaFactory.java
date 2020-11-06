package com.jp.dark.factory;

import com.jp.dark.dtos.VisitaDTO;

import java.util.Optional;

public class VisitaFactory {

    public static Optional<VisitaDTO> createAnyVisita() {
        return Optional.of(VisitaDTO.builder()
                .recomendacao("Realizar analise de solo urgente.")
                .situacao("Pastagem degradada.")
                .build());
    }

    public static VisitaDTO createNewValidVisitaDto() {
        return VisitaDTO.builder()
                .recomendacao("Realizar analise de solo urgente.")
                .situacao("Pastagem degradada.")
                .build();
    }
    public static VisitaDTO createSavedVisitaDto() {
        return VisitaDTO.builder()
                .codigo("202011052234")
                .recomendacao("Realizar analise de solo urgente.")
                .situacao("Pastagem degradada.")
                .build();
    }
}
