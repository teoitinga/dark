package com.jp.dark.services;

import com.jp.dark.config.Config;
import com.jp.dark.dtos.ProdutorMinDTO;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.factory.CallFactory;
import com.jp.dark.factory.PersonaFactory;
import com.jp.dark.factory.ProdutorFactory;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.models.repository.ServiceProvidedRepository;
import com.jp.dark.services.impls.CallServiceImpl;
import com.jp.dark.services.impls.PersonaServiceImpl;
import com.jp.dark.services.impls.ServiceProvidedServiceImpl;
import com.jp.dark.services.impls.VisitaServiceImpl;
import com.jp.dark.utils.GeraCpfCnpj;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class VisitaServiceTest {

    VisitaService service;

    @MockBean
    VisitaRepository visitaRepository;

    @MockBean
    PersonaService personaService;

    @MockBean
    CallRepository callRepository;

    @MockBean
    PersonaRepository personaRepository;

    @MockBean
    GeraCpfCnpj verifyCpf;

    @MockBean
    private ServiceProvidedRepository serviceProvidedRepository;

    @MockBean
    Config config;

    @MockBean
    CallService callService;

    @MockBean
    private ServiceProvidedService serviceProvidedService;

    @BeforeEach
    public void setup(){
        config = new Config();
        this.service = new VisitaServiceImpl(visitaRepository, callRepository, personaRepository, serviceProvidedRepository, config);
        this.personaService = new PersonaServiceImpl(personaRepository);
        this.callService = new CallServiceImpl(callRepository, config, personaRepository, serviceProvidedRepository, visitaRepository);
        this.serviceProvidedService = new ServiceProvidedServiceImpl(serviceProvidedRepository);
    }

//    @Test
//    @DisplayName("Deve registrar uma visita válida.")
//    public void saveTest(){
//        //cenario
//        VisitaDTO visita = createValidVisita();
//
//        Visita savedVisita = VisitaFactory.createVisitaSaved();
//
//        Persona validPersona = PersonaFactory.createValidPersona();
//        ProdutorMinDTO produtor = ProdutorFactory.createProdutorMinDto();
//
//
//        Mockito.when( verifyCpf.isCPF(Mockito.anyString() )).thenReturn(false);
//        Mockito.when( personaService.personaExists(Mockito.anyString() )).thenReturn(false);
//        Mockito.when( personaService.findByCpf(Mockito.anyString() )).thenReturn(PersonaFactory.createValidPersona());
//        Mockito.when( personaService.save(Mockito.any(ProdutorMinDTO.class) )).thenReturn(ProdutorFactory.createNewPersona());
//        Mockito.when( visitaRepository.save(Mockito.any(Visita.class)) ).thenReturn(savedVisita);
//        Mockito.when( serviceProvidedService.serviceExists(Mockito.anyString()) ).thenReturn(true);
//        Mockito.when( callService.save((CallFactory.createListWith3Call())) ).thenReturn(CallFactory.createListWith3Call());
//        Mockito.when(personaRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.of(PersonaFactory.createValidPersona()));
//
////        Mockito.when( personaService.PersonaExists(Mockito.anyString() )).thenReturn(true);
////        Mockito.when( personaRepository.findByCpf(Mockito.anyString() )).thenReturn(Optional.of(PersonaFactory.createValidPersona()));
////        Mockito.when( personaService.save(Mockito.any(ProdutorMinDTO.class)) ).thenReturn(PersonaFactory.createValidPersona());
////        Mockito.when( verifyCpf.isCPF(Mockito.anyString()) ).thenReturn(false);
////        Mockito.when( visitaRepository.existsByCodigo(visita.getCodigoVisita()) ).thenReturn(false);
////        Mockito.when( visitaRepository.findByCodigo(Mockito.anyString()) ).thenReturn(null);
//
//        //execução
//        VisitaDTO savedDto = service.save(visita);
//
//        //verificação
//        assertThat(savedDto.getCodigoVisita()).isNotEmpty();
//        assertThat(savedDto.getRecomendacao()).isEqualTo("Adubação fosfatado na época do plantio");
//        assertThat(savedDto.getSituacaoAtual()).isEqualTo("Apresenta baixa produção e custos altos");
//        assertThat(savedDto.getProdutores().size()).isEqualTo(5);
//        assertThat(savedDto.getChamadas().size()).isEqualTo(10);
//
//
//
//    }

    private Visita createValidVisitaEntity() {
        return VisitaFactory.createVisitaEntity();
    }

    private VisitaDTO createValidVisita() {
        return VisitaFactory.createVisitaDto();
    }

//    @Test
//    @DisplayName("Deve lançar erro de negocio ao tentar salvar visita com ID duplicado.")
//    public void shouldNotSaveVisitaWithDuplicatedId(){
//
//        //cenario
//        VisitaDTO visita = createValidVisita();
//
//        String error_Message = "Identificador já existe";
//        BDDMockito.when(visitaRepository.findByCodigo(Mockito.anyString()))
//                .thenReturn(VisitaFactory.createSavedVisita());
//        //execução
//        Throwable exception = Assertions.catchThrowable( () -> service.save( visita ) );
//
//        //verificações
//        assertThat(exception)
//                .isInstanceOf(BusinessException.class)
//                .hasMessage(error_Message)
//                ;
//
//        //verificando se chamou a funcao save()
//        Mockito.verify(visitaRepository, Mockito.never()).save(Mockito.any(Visita.class));
//        Mockito.verify(visitaRepository, Mockito.times(0)).save(Mockito.any(Visita.class));
//
//    }
    @Test
    @DisplayName("Deve retornar um objeto VisitaDto se for encontrada no banco de dados.")
    public void getByCodigoTest(){
        String id = "2020101005";

        Mockito.when( visitaRepository.findByCodigo( id ) ).thenReturn( VisitaFactory.createSavedVisita() );

        VisitaDTO visita = this.service.getByCodigo(id);

        //verificando se chamou a funcao save()
        Mockito.verify(visitaRepository, Mockito.times(1)).findByCodigo(Mockito.anyString());
        assertThat(visita.getCodigoVisita()).isEqualTo(id);
        assertThat(visita.getRecomendacao()).isEqualTo("Procurar o IEG/SUPRAM");
        assertThat(visita.getSituacaoAtual()).isEqualTo("Pastagem degradada.");
    }

    @Test
    @DisplayName("Deve deletar uma visita")
    public void delete(){
        Visita visita = VisitaFactory.createNewValidVisita();

        visita.setCodigo("2020");
        Mockito.when( visitaRepository.findByCodigo( "2020" ) ).thenReturn(Optional.of(visita) );

        //execução
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(()->service.delete(visita));

        //verificações
        Mockito.verify(visitaRepository, Mockito.times(1)).delete(visita);

    }
    @Test
    @DisplayName("Deve ocorrer erro ao deletar uma visita inexistente")
    public void deleteInvalidVisitaTest(){
        Visita visita = new Visita();

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(VisitaNotFoundException.class, ()->service.delete(visita));


        //verificações
        Mockito.verify(visitaRepository, Mockito.never()).delete(visita);
    }

    @Test
    @DisplayName("Deve ocorrer erro ao deletar uma visita inexistente")
    public void deleteNullVisitaTest(){
        Visita visita = VisitaFactory.createVisitaEntity();

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(VisitaNotFoundException.class, ()->service.delete(visita));

        //verificações
        Mockito.verify(visitaRepository, Mockito.never()).delete(visita);
    }
    @Test
    @DisplayName("Deve checkar se o produtor está registrado no Banco de dados e salvar, caso noecessário.")
    public void checkTest(){
        ProdutorMinDTO prd = ProdutorFactory.createProdutorMinDto();

        Mockito.when(personaService.personaExists(Mockito.anyString())).thenReturn(true);
        Mockito.when(personaRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.of(PersonaFactory.createValidPersona()));
        Persona checked = this.service.check(prd);

        assertThat(checked).isNotNull();
        Mockito.verify(personaRepository, Mockito.never()).save(Mockito.any(Persona.class));
    }
    @Test
    @DisplayName("Deve checkar se o produtor está registrado no Banco de dados e salvar.")
    public void checkNoExistTest(){
        ProdutorMinDTO prd = ProdutorFactory.createProdutorMinDto();

        Mockito.when(personaService.personaExists(Mockito.anyString())).thenReturn(false);
        Mockito.when(personaService.save(Mockito.any(ProdutorMinDTO.class))).thenReturn(PersonaFactory.createValidPersona());
        Mockito.when(personaRepository.save(Mockito.any(Persona.class))).thenReturn(PersonaFactory.createValidPersona());

        Persona checked = this.service.check(prd);

        assertThat(checked).isNotNull();
        Mockito.verify(personaRepository, Mockito.times(1)).save(Mockito.any(Persona.class));
    }

}
