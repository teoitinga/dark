package com.jp.dark.services;

import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.factory.CallFactory;
import com.jp.dark.factory.ProdutorFactory;
import com.jp.dark.factory.ServiceProvidedFactory;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.repository.ServiceProvidedRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @BeforeEach
    public void setup(){
        //this.service = new CallServiceImpl(callRepository, visitaRepository, personaRepository, serviceProvidedRepository);
    }
    @Test
    @DisplayName("Deve registrar uma chamada válida.")
    public void saveTest(){
        CallDTO call = CallFactory.createCallDto();
        Call callmodified = CallFactory.createCall();

        //VisitaDTO visita = VisitaFactory.createVisitaDto();
        Visita visita = VisitaFactory.createVisitaEntity();


        CallDTO savedDto = this.service.save(call, visita);

        //verificação
        assertThat(savedDto.getCodigo()).isNotEmpty();
        assertThat(savedDto.getCodigo()).isEqualTo("202010101010");
        assertThat(savedDto.getOcorrencia()).isEqualTo("Realizar analise de solo.");
        assertThat(savedDto.getStatus()).isEqualTo("iniciada");
    }
}
