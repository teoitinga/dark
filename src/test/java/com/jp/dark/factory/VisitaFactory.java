package com.jp.dark.factory;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.models.entities.Visita;

import java.util.Optional;

public class VisitaFactory {

    public static Optional<VisitaDTO> createAnyVisitaDto() {
        return Optional.of(VisitaDTO.builder()
                .recomendacao("Realizar analise de solo urgente.")
                .situacao("Pastagem degradada.")
                .build());
    }
    public static Optional<Visita> createAnyVisita() {
        return Optional.of(Visita.builder()
                .recomendacao("Realizar analise de solo urgente.")
                .situacao("Pastagem degradada.")
                .build());
    }
    public static Optional<Visita> createSavedVisita() {
        return Optional.of(Visita.builder()
                .codigo("202011052234")
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

    public static Visita createNewValidVisita() {
        return Visita.builder()
                .codigo("202011052234")
                .recomendacao("Realizar analise de solo urgente.")
                .situacao("Pastagem degradada.")
                .build();
    }

    public static Visita createVisita() {
        return Visita.builder()
                .recomendacao("Realizar analise de solo urgente.")
                .situacao("Pastagem degradada.")
                .build();
    }
    public static VisitaDTO createVisitaDto() {
        return VisitaDTO.builder()
                .codigo("20201010")
                .recomendacao("Realizar analise de solo urgente.")
                .situacao("Pastagem degradada.")
                .build();
    }
}
