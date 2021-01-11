package com.jp.dark.factory;

import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.models.entities.ServiceProvided;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServiceProvidedFactory {

    public static ServiceProvided createServiceProvided(){

        return ServiceProvided.builder()
                .codigo("CAR")
                .defaultValue(new BigDecimal(100))
                .descricao("Cadastro Ambiental Rural")
                .referency("Elaboração de Cadastro Ambiental Rural")
                .timeRemaining(5)
                .build();
    }
    public static ServiceProvided createLMServiceProvided(){

        return ServiceProvided.builder()
                .codigo("LM")
                .defaultValue(new BigDecimal(100))
                .descricao("Limite de crédito")
                .referency("Elaboração de laudo")
                .timeRemaining(5)
                .build();
    }

    public static ServiceProvidedDTO createServiceProvidedDTO() {
        return ServiceProvidedDTO.builder()
                .codigo("CAR")
                .defaultValue(new BigDecimal(100))
                .descricao("Cadastro Ambiental Rural")
                .referency("Elaboração de Cadastro Ambiental Rural")
                .timeRemaining(5)
                .build();
    }

    public static List<ServiceProvided> createListServiceProvided() {
        List<ServiceProvided> list = new ArrayList<>();
        list.add(createServiceProvided());
        list.add(createLMServiceProvided());
        return list;
    }
}
