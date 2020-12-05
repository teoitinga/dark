package com.jp.dark.config;

import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.models.enums.EnumCategoria;
import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.repository.ServiceProvidedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader {

    private final PersonaRepository personaRepository;

    private ServiceProvidedRepository serviceRepository;

    @Autowired
    public DataLoader(ServiceProvidedRepository serviceRepository,
                      PersonaRepository personaRepository) {
        this.serviceRepository = serviceRepository;
        this.personaRepository = personaRepository;
        LoadServicesProvided();
        LoadUsers();

    }

    private void LoadUsers() {
        this.personaRepository.save(new Persona("04459471604", "João Paulo Santana Gusmão", "33999065029", LocalDate.of(1979,1,4),"Rua José Tonel, 56",
                "35140000","Tarumirim","jacare", EnumCategoria.UNDEFINED, EnumPermissao.ADMINISTRADOR));
    }

    private void LoadServicesProvided() {
        serviceRepository.save(new ServiceProvided("LAS", "Cadastro Ambiental Rural", "Cadastro", new BigDecimal(150), 3));
        serviceRepository.save(new ServiceProvided("LM", "Limite de credito", "Elaboração de laudo", new BigDecimal(150), 5));
        serviceRepository.save(new ServiceProvided("ATER", "Ater Bovinocultura", "Assist. Pastagem", new BigDecimal(150), 1));
    }
}
