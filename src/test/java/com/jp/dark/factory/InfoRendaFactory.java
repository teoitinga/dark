package com.jp.dark.factory;

import com.jp.dark.dtos.InfoRendaDTO;

import java.time.LocalDate;

public class InfoRendaFactory {
    public static InfoRendaDTO createValidInfoRenda() {
        return InfoRendaDTO.builder()
                .codigoVisita("20201212")
                .producaoAnual(ProducaoFactory.createListItemProducao())
                .createFolder(true)
                .localDoAtendimeno("Faz. Barão Vermelho")
                .dataDaVisita("2020-09-21")
                .situacaoAtual("Precisa atualizar o cadastro junto a agencia financeira")
                .orientacao("Providenciar documentação comprobatoria")
                .recomendacao("Realizar o ajuste anual de imposto de renda")
                .produtores(ProdutorFactory.createList5ValidProdutors())
                .build();
    }
    public static InfoRendaDTO createSavedInfoRenda() {
        return InfoRendaDTO.builder()
                .codigoVisita("20201212")
                .producaoAnual(ProducaoFactory.createListItemProducao())
                .createFolder(true)
                .localDoAtendimeno("Faz. Barão Vermelho")
                .dataDaVisita("2020-09-21")
                .situacaoAtual("Precisa atualizar o cadastro junto a agencia financeira")
                .orientacao("Providenciar documentação comprobatoria")
                .recomendacao("Realizar o ajuste anual de imposto de renda")
                .produtores(ProdutorFactory.createList5ValidProdutors())
                .build();
    }
}
