package com.jp.dark.factory;

import com.jp.dark.dtos.ServiceProvidedDTO;
import com.jp.dark.models.entities.ServiceProvided;

import java.math.BigDecimal;

public class ServiceProvidedFactory {

    public static ServiceProvided createServiceProvided(){

        return ServiceProvided.builder()
                .codigo("202010111010")
                .defaultValue(new BigDecimal(100))
                .descricao("Cadastro Ambiental Rural")
                .referency("Elaboração de Cadastro Ambiental Rural")
                .timeRemaining(5)
                .build();
    }

    public static ServiceProvidedDTO createServiceProvidedDTO() {
        return ServiceProvidedDTO.builder()
                .codigo("202010111010")
                .defaultValue(new BigDecimal(100))
                .descricao("Cadastro Ambiental Rural")
                .referency("Elaboração de Cadastro Ambiental Rural")
                .timeRemaining(5)
                .build();
    }
}
