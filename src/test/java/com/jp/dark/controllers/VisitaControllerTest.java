package com.jp.dark.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.services.VisitaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class VisitaControllerTest {

    static String API = "/api/v1/visitas";

    @Autowired
    MockMvc mvc;

    @MockBean
    VisitaService service;

    @Test
    @DisplayName("Deve criar uma visita com sucesso.")
    public void creaeteVisitaTest() throws Exception{

        VisitaDTO dto =  VisitaFactory.createNewValidVisitaDto();

        VisitaDTO savedDto =  VisitaFactory.createNewValidVisitaDto();
        savedDto.setCodigo("2020");

        BDDMockito.given(service.save(Mockito.any(VisitaDTO.class))).willReturn(savedDto);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("codigo").isNotEmpty())
                .andExpect(jsonPath("recomendacao").value(dto.getRecomendacao()))
                ;
    }

    private VisitaDTO ccreateNewValidVisitaDto() {
        return VisitaFactory.createNewValidVisitaDto();
    }

    @Test
    @DisplayName("Deve lançar erro de validação ao criar uma visita.")
    public void createInvalidVisitaTest() throws Exception {

        VisitaDTO dto = VisitaDTO.builder()
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

       mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(2)))
                ;
    }
    @Test
    @DisplayName("Deve atualizar uma visita.")
    public void updateVisitaTest() throws Exception {

        String codigo = "202011052234";

        VisitaDTO dto =  VisitaFactory.createNewValidVisitaDto();

        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(service.getByCodigo(Mockito.anyString())).willReturn(Optional.of(VisitaFactory.createSavedVisitaDto()));
        BDDMockito.given(service.update(Mockito.any(VisitaDTO.class))).willReturn( VisitaFactory.createSavedVisitaDto() );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/".concat(codigo)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //verificações
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("codigo").isNotEmpty())
                .andExpect(jsonPath("codigo").value( codigo ))
                .andExpect(jsonPath("recomendacao").value( dto.getRecomendacao() ))
                .andExpect(jsonPath("situacao").value( dto.getSituacao() ))
                ;
    }

    @Test
    @DisplayName("Deve retornar 404 ao tentar atualizar uma visita inexistente.")
    public void updateVisitaNotFoundTest() throws Exception {
        String codigo = "20201104";

        VisitaDTO dto =  VisitaFactory.createNewValidVisitaDto();

        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(service.getByCodigo(Mockito.anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API.concat("/".concat(codigo)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        //verificações
        mvc.perform(request)
                .andExpect(status().isNotFound())
        ;
    }
    @Test
    @DisplayName("Deve obter informações da visita")
    public void getDetailsVisitaTest() throws Exception {
        String codigo = "20201104";

        VisitaDTO visita =  VisitaFactory.createNewValidVisitaDto();
        visita.setCodigo(codigo);

        BDDMockito.given(service.getByCodigo(codigo)).willReturn(Optional.of(visita));

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/" + codigo))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("codigo").isNotEmpty())
                .andExpect(jsonPath("recomendacao").value(visita.getRecomendacao()))
                .andExpect(jsonPath("situacao").value(visita.getSituacao()))
                ;
    }
    @Test
    @DisplayName("Deve retornar VisitaNotFoundException ao obter informações da visita que não existe.")
    public void getDetailsVisitaNotFoundTest() throws Exception {
        String codigo = "20201104";

        VisitaDTO visita =  VisitaFactory.createNewValidVisitaDto();
        visita.setCodigo(codigo);

        BDDMockito.given(service.getByCodigo(codigo)).willThrow(new VisitaNotFoundException());

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/" + codigo))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Visita não encontrada."))
        ;
    }
    @Test
    @DisplayName("Deve lançar erro se o coidigo for duplicado - exemplo de regra de negoicio")
    public void createVisitaWithDuplicatedId() throws Exception {

        VisitaDTO dto =  VisitaFactory.createNewValidVisitaDto();

        String json = new ObjectMapper().writeValueAsString(dto);

        String error_Message = "Identificador já existe";

        BDDMockito.given(service.save(Mockito.any(VisitaDTO.class)))
                .willThrow(new BusinessException(error_Message));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value(error_Message))
                ;
    }
}
