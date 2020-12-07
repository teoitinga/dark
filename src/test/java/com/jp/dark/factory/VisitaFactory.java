package com.jp.dark.factory;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.models.entities.Visita;

import java.time.LocalDate;
import java.util.Optional;

public class VisitaFactory {

    public static VisitaDTO createNewValidVisitaDto() {
        return VisitaDTO.builder()
                .localDoAtendimeno("Faz. Saudade")
                .chamadas(CallFactory.createListWith3CallDTO())
                .produtores(ProdutorFactory.createList5ValidProdutors())
                .recomendacao("Realizar coleta de amostra de solo")
                .dataDaVisita(LocalDate.of(2020,10,25).toString())
                .orientacao("Fazer interpretação de análise de solo")
                .situacaoAtual("Não produz bosta nenhuma")
                .build();
    }

    public static VisitaDTO createSavedVisitaDto() {
        VisitaDTO response =createNewValidVisitaDto();
        response.setCodigoVisita("20201010");
        return  response;
    }

    public static VisitaDTO createVisitaDto() {
        return VisitaDTO.builder()
                .codigoVisita("20201129")
                .situacaoAtual("Apresenta baixa produção e custos altos")
                .orientacao("Realizar coleta e amostra de solo")
                .dataDaVisita(LocalDate.of(2020,11,19).toString())
                .recomendacao("Adubação fosfatado na época do plantio")
                .produtores(ProdutorFactory.createList5ValidProdutors())
                .chamadas(CallFactory.createListWith3CallDTO())
                .localDoAtendimeno("Faz Bom sucesso")
                .build();
    }
    public static Visita createVisitaSaved() {
        return Visita.builder()
                .codigo("20201129")
                .situacao("Apresenta baixa produção e custos altos")
                .orientacao("Realizar coleta e amostra de solo")
                .dataDaVisita(LocalDate.of(2020,11,19))
                .recomendacao("Adubação fosfatado na época do plantio")
                .produtores(ProdutorFactory.createList5ValidPersona())
                .chamadas(CallFactory.createListWith3Call())
                .localDoAtendimento("Faz Bom sucesso")
                .build();
    }

    public static Visita createVisitaEntity() {
        return Visita.builder()
                .codigo("2020101002")
                .localDoAtendimento("Faz. Cilada")
                .produtores(ProdutorFactory.createList5ValidPersona())
                .orientacao("Fazer interpretação de análise de solo")
                .recomendacao("Realizar coleta de amostra de solo")
                .dataDaVisita(LocalDate.of(2020,10,25))
                .situacao("Não produz porra nenhuma")
                .build();
    }

    public static Optional<Visita> createSavedVisita() {
        return Optional.of(Visita.builder()
                .codigo("2020101005")
                .localDoAtendimento("Faz. Caparaó")
                .produtores(ProdutorFactory.createList5ValidPersona())
                .orientacao("Obter a outorga para uso da água")
                .recomendacao("Procurar o IEG/SUPRAM")
                .dataDaVisita(LocalDate.of(2020,5,12))
                .situacao("Pastagem degradada.")
                .build());
    }

    public static Visita createNewValidVisita() {
        return Visita.builder()
                .codigo("2020101005")
                .localDoAtendimento("Faz. Caparaó")
                .produtores(ProdutorFactory.createList5ValidPersona())
                .orientacao("Obter a outorga para uso da água")
                .recomendacao("Procurar o IEG/SUPRAM")
                .dataDaVisita(LocalDate.of(2020,5,12))
                .situacao("Pastagem degradada.")
                .build();
    }
}
