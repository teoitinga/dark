package com.jp.dark.services;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.services.impls.VisitaServiceImpl;
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
public class VisitaServiceTest {

    VisitaService service;

    @MockBean
    VisitaRepository repository;

    @BeforeEach
    public void setup(){
        this.service = new VisitaServiceImpl(repository);
    }
    @Test
    @DisplayName("Deve registrar uma visita válida.")
    public void saveTest(){
        //cenario
        VisitaDTO visita = createValidVisita();

        Visita savedVisita = Visita.builder()
                .codigo("202011040945")
                .recomendacao("realizar analise de solo.")
                .situacao("Pastagem degradada")
                .build();

        Mockito.when( repository.existsByCodigo(visita.getCodigo()) ).thenReturn(false);
        Mockito.when( repository.save(Mockito.any(Visita.class)) ).thenReturn(savedVisita);

        //execução
        VisitaDTO savedDto = service.save(visita);
        //verificação
        assertThat(savedDto.getCodigo()).isNotEmpty();
        assertThat(savedDto.getCodigo()).isEqualTo("202011040945");
        assertThat(savedDto.getRecomendacao()).isEqualTo("realizar analise de solo.");
        assertThat(savedDto.getSituacao()).isEqualTo("Pastagem degradada");

    }

    private VisitaDTO createValidVisita() {
        return VisitaDTO.builder()
                .recomendacao("realizar analise de solo.")
                .situacao("Pastagem degradada")
                .build();
    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar visita com ID duplicado.")
    public void shouldNotSaveVisitaWithDuplicatedId(){

        //cenario
        VisitaDTO visita = createValidVisita();

        String error_Message = "Identificador já existe";

        Mockito.when( repository.existsByCodigo( visita.getCodigo() ) ).thenReturn( true );

        //execução
        Throwable exception = Assertions.catchThrowable( () -> service.save( visita ) );

        //verificações
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage(error_Message)
                ;

        //verificando se chamou a funcao save()
        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Visita.class));
        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Visita.class));

    }
}
