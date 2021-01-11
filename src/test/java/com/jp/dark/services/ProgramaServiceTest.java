package com.jp.dark.services;

import com.jp.dark.dtos.MultiplosBeneficiariosDTO;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.factory.*;
import com.jp.dark.models.entities.Beneficiario;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.ServiceProvided;
import com.jp.dark.models.repository.BeneficiarioRepository;
import com.jp.dark.models.repository.ProgramaRepository;
import com.jp.dark.services.impls.CallServiceImpl;
import com.jp.dark.services.impls.ProgramaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProgramaServiceTest {

    ProgramaService service;

    @MockBean
    ProgramaRepository repository;

    @MockBean
    PersonaService personaService;

    @MockBean
    BeneficiarioRepository beneficiarioRepository;

    @MockBean
    ServiceProvidedService serviceProvided;

    @MockBean
    CallServiceImpl callService;

    @MockBean
    VisitaService visitaService;

    @MockBean
    ProgramaService programaService;

    @BeforeEach
    public void setup(){
        this.service = new ProgramaServiceImpl(repository,
                personaService,
                beneficiarioRepository,
                serviceProvided,
                callService,
                visitaService
                );
    }

    @Test
    @DisplayName("Deve checkar os dados informados do produtor e salvar o registro,")
    public void checkTest(){
        ProdutorMinDTO prd = ProdutorFactory.createProdutorMinDto();

        BDDMockito.when(this.personaService.personaExists(Mockito.anyString())).thenReturn(false);
        BDDMockito.when(this.personaService.findByCpf(Mockito.anyString())).thenReturn(PersonaFactory.createValidPersona());
        BDDMockito.when(this.personaService.save(Mockito.any(ProdutorMinDTO.class))).thenReturn(PersonaFactory.createValidPersona());

        Persona checked = this.service.check(prd);

        //verificação
        assertThat(checked).isNotNull();
        //verificando se chamou a funcao save()
        Mockito.verify(personaService, Mockito.never()).findByCpf(Mockito.anyString());
        Mockito.verify(personaService, Mockito.times(1)).save(Mockito.any(ProdutorMinDTO.class));
        Mockito.verify(personaService, Mockito.times(0)).findByCpf(Mockito.anyString());

    }
    @Test
    @DisplayName("Deve checkar os dados informados do produtor e retornar o registro sem salvar.")
    public void checkProdutorNoExistTest(){
        ProdutorMinDTO prd = ProdutorFactory.createProdutorMinDto();

        BDDMockito.when(this.personaService.personaExists(Mockito.anyString())).thenReturn(true);
        BDDMockito.when(this.personaService.findByCpf(Mockito.anyString())).thenReturn(PersonaFactory.createValidPersona());
        BDDMockito.when(this.personaService.save(Mockito.any(ProdutorMinDTO.class))).thenReturn(PersonaFactory.createValidPersona());

        Persona checked = this.service.check(prd);

        //verificação
        assertThat(checked).isNotNull();
        //verificando se chamou a funcao save()
        Mockito.verify(personaService, Mockito.never()).save(Mockito.any(ProdutorMinDTO.class));
        Mockito.verify(personaService, Mockito.times(0)).save(Mockito.any(ProdutorMinDTO.class));
        Mockito.verify(personaService, Mockito.times(1)).findByCpf(Mockito.anyString());

    }
}
