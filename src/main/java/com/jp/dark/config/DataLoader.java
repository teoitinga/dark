package com.jp.dark.config;

import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.repository.ServiceProvidedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader {
    private ServiceProvidedRepository serviceRepository;

    @Autowired
    public DataLoader(ServiceProvidedRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
        LoadUsers();
    }

    private void LoadUsers() {
        serviceRepository.save(new ServiceProvided("LAS", "Cadastro Ambiental Rural", "Cadastro", new BigDecimal(150), 3));
        serviceRepository.save(new ServiceProvided("LM", "Limite de credito", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("ATER", "Ater Bovinocultura", "Assist. Pastagem", new BigDecimal(150), 1));
    }
}
