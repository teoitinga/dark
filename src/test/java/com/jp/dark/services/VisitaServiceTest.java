package com.jp.dark.services;

import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.factory.VisitaFactory;
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
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @Test
    @DisplayName("Deve retornar um objeto VisitaDto se for encontrada no banco de dados.")
    public void getByCodigoTest(){
        String id = "202011052234";

         Mockito.when( repository.findByCodigo( Mockito.anyString() ) ).thenReturn( VisitaFactory.createSavedVisita() );

        Optional<VisitaDTO> visita = this.service.getByCodigo(id);

        //verificando se chamou a funcao save()
        Mockito.verify(repository, Mockito.times(1)).findByCodigo(Mockito.anyString());
        assertThat(visita.isPresent()).isTrue();
        assertThat(visita.get().getCodigo()).isEqualTo(id);
        assertThat(visita.get().getRecomendacao()).isEqualTo("Realizar analise de solo urgente.");
        assertThat(visita.get().getSituacao()).isEqualTo("Pastagem degradada.");
    }
    @Test
    @DisplayName("Deve retornar vazio ao tentar obter uma visita cujo código não existe no BD.")
    public void getByCodigoNotFoundTest(){
        String id = "202011052234";

         Mockito.when( repository.findByCodigo( Mockito.anyString() ) ).thenReturn( Optional.empty() );

        Optional<VisitaDTO> visita = this.service.getByCodigo(id);

        //verificando se chamou a funcao save()
        Mockito.verify(repository, Mockito.times(1)).findByCodigo(Mockito.anyString());
        assertThat(visita.isPresent()).isFalse();

    }
    @Test
    @DisplayName("Deve deletar uma visita")
    public void delete(){
        Visita visita = VisitaFactory.createNewValidVisita();

        //execução
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(()->service.delete(visita));

        //verificações
        Mockito.verify(repository, Mockito.times(1)).delete(visita);

    }
    @Test
    @DisplayName("Deve ocorrer erro ao deletar uma visita inexistente")
    public void deleteInvalidVisitaTest(){
        Visita visita = new Visita();

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(VisitaNotFoundException.class, ()->service.delete(visita));


        //verificações
        Mockito.verify(repository, Mockito.never()).delete(visita);
    }
    @Test
    @DisplayName("Deve filtrar as propriedade da visita.")
    public void findTest(){

        Visita visita = VisitaFactory.createNewValidVisita();

        VisitaDTO visitaDto = VisitaFactory.createNewValidVisitaDto();

        List<Visita> list = Arrays.asList(visita);

        long totalElements = 1;

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Visita> page = new PageImpl<Visita>(list, pageRequest, totalElements);

        Mockito.when( repository.findAll(
                Mockito.any(Example.class),
                Mockito.any(Pageable.class)
                ) )
                .thenReturn(page);

        Page<VisitaDTO> result = service.find(visitaDto, pageRequest);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);

    }
    @Test
    @DisplayName("Deve ocorrer erro ao deletar uma visita inexistente")
    public void deleteNullVisitaTest(){
        Visita visita = null;

        //execução
        org.junit.jupiter.api.Assertions.assertThrows(VisitaNotFoundException.class, ()->service.delete(visita));


        //verificações
        Mockito.verify(repository, Mockito.never()).delete(visita);
    }
    @Test
    @DisplayName("Deve atualizar a visita")
    public void updateTest(){
        //visita a atualizar
        Visita visita = VisitaFactory.createNewValidVisita();
        //visita.setCodigo("20201105");

        VisitaDTO visitaDto = new VisitaDTO();

        //simula a atualização
        Visita updatingVisita = VisitaFactory.createNewValidVisita();

        Mockito.when(repository.save(Mockito.any(Visita.class))).thenReturn(updatingVisita);

        //execução
        visitaDto = service.update(visitaDto);

        //verificações
        assertThat(visitaDto.getCodigo()).isEqualTo(visita.getCodigo());
        assertThat(visitaDto.getRecomendacao()).isEqualTo(visita.getRecomendacao());
        assertThat(visitaDto.getSituacao()).isEqualTo(visita.getSituacao());

    }
}
