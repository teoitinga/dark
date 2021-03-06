package com.jp.dark.services;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.CallDTO;
import com.jp.dark.factory.CallFactory;
import com.jp.dark.factory.PersonaFactory;
import com.jp.dark.factory.ServiceProvidedFactory;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.models.repository.ServiceProvidedRepository;
import com.jp.dark.services.impls.CallServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Slf4j
public class CallServiceTest {

    CallService service;

    @MockBean
    VisitaService visitaService;

    @MockBean
    CallRepository callRepository;

    @MockBean
    VisitaRepository visitaRepository;

    @MockBean
    PersonaRepository personaRepository;

    @MockBean
    ServiceProvidedRepository serviceProvidedRepository;

    Config config;

    @MockBean
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup(){
        this.config = new Config();
        this.service = new CallServiceImpl(callRepository, config, personaRepository, serviceProvidedRepository, visitaRepository, passwordEncoder);
    }
    @Test
    @DisplayName("Deve registrar uma chamada válida.")
    public void saveTest(){
        CallDTO call = CallFactory.createCallDto();
        Call callmodified = CallFactory.createCall();

        Visita visita = VisitaFactory.createVisitaEntity();

        Mockito.when(serviceProvidedRepository.findByCodigo(Mockito.anyString()))
                .thenReturn(Optional.of(ServiceProvidedFactory.createServiceProvided()));

        Mockito.when(personaRepository.findByCpf(Mockito.anyString()))
                .thenReturn(Optional.of(PersonaFactory.createValidPersona()));

        Mockito.when(callRepository.save(Mockito.any(Call.class)))
                .thenReturn(CallFactory.createCall());

        CallDTO savedDto = this.service.save(call, visita);

        //verificação
        assertThat(savedDto.getCodigo()).isNotEmpty();
        assertThat(savedDto.getCodigo()).isEqualTo("2001");
        assertThat(savedDto.getOcorrencia()).isEqualTo("No ocurrency");
        assertThat(savedDto.getStatus()).isEqualTo("CANCELADA");
    }
}
