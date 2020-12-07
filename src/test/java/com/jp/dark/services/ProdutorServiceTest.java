package com.jp.dark.services;

import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.exceptions.PersonaAlreadyExistsException;
import com.jp.dark.factory.PersonaFactory;
import com.jp.dark.factory.ProdutorFactory;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.impls.PersonaServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProdutorServiceTest {

    PersonaService service;

    @MockBean
    PersonaRepository repository;

    @BeforeEach
    public void setup(){
        this.service = new PersonaServiceImpl(repository);
    }
    @Test
    @DisplayName("Deve registrar um produtor válido.")
    public void saveTest(){
        ProdutorDTO produtor = ProdutorFactory.createNewProdutorDto();

        Mockito.when(this.repository.save(Mockito.any(Persona.class))).thenReturn(ProdutorFactory.createSavedFabio());

        //execução
        ProdutorDTO savedDto = service.save(produtor);

        //verificação
        assertThat(savedDto.getCpf()).isNotEmpty();
        assertThat(savedDto.getCpf()).isEqualTo("34546628692");
        assertThat(savedDto.getNome()).isEqualTo("Fábio Cauã Moreira");
    }
    @Test
    @DisplayName("Deve retornar erro caso já exist um produtor com o CPF registrado.")
    public void saveWithCPfDuplicatedTest(){
        ProdutorDTO produtor = ProdutorFactory.createNewProdutorDto();

        ProdutorDTO savedprodutor = ProdutorFactory.createNewProdutorDto();
        Persona saved = ProdutorFactory.createNewPersona();

        Mockito.when( repository.existsByCpf(Mockito.anyString()) ).thenReturn(true);
        Mockito.when( repository.save(Mockito.any(Persona.class)) ).thenReturn(ProdutorFactory.createValidBryan());

        String error_Message = "Persona already exists and cannot be overwritten";

        //execução
        Throwable exception = Assertions.catchThrowable( () -> service.save( produtor ) );

        //verificações
        assertThat(exception)
                .isInstanceOf(PersonaAlreadyExistsException.class)
                .hasMessage(error_Message)
        ;

        //verificando se chamou a funcao save()
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Persona.class));
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Persona.class));

    }
}
