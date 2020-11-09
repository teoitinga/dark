package com.jp.dark.factory;

import com.jp.dark.dtos.CallDTO;

public class CallFactory {

    public static CallDTO createNewCallDto() {
        return  CallDTO.builder()
                .ocorrencia("Sem ocorrências")
                .servico("Emissão de DAP")
                .codigoVisita("202011041922")
                .build();
    }

    public static CallDTO createSavedCallDto() {

        CallDTO response = createNewCallDto();
        response.setCodigo("202011");

        return response;
    }
}
