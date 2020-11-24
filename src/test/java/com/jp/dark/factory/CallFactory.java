package com.jp.dark.factory;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.models.entities.Call;

public class CallFactory {

    public static CallDTO createNewCallDto() {
        return  CallDTO.builder()
                .codigo("20201011")
                .ocorrencia("Sem ocorrências")
                .servico("Emissão de DAP")
                .visita(VisitaFactory.createVisitaDto())
                .produtores(ProdutorFactory.createList5ValidProdutors())
                .build();
    }

    public static CallDTO createSavedCallDto() {

        CallDTO response = createNewCallDto();
        response.setCodigo("202011");

        return response;
    }

    public static Call createNewCall() {
        return  Call.builder()
                .ocorrencia("Sem ocorrências")
                .servico("Emissão de DAP")
                .visita(VisitaFactory.createNewValidVisita())
                .build();
    }
    public static Call createSavedCall() {
        return  Call.builder()
                .codigo("202010101010")
                .ocorrencia("Realizar analise de solo.")
                .servico("Emissão de DAP")
                .visita(VisitaFactory.createNewValidVisita())
                .produtores(ProdutorFactory.createList5ValidPersona())
                .build();
    }
}
