package com.jp.dark.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.BusinessException;
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

        VisitaDTO dto = createNewValidVisitaDto();

        VisitaDTO savedDto = createNewValidVisitaDto();
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

    private VisitaDTO createNewValidVisitaDto() {
        return VisitaDTO.builder()
                .recomendacao("Realizar analise ded solo urgente.")
                .situacao("Pastagem degradada.")
                .build();
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
    @DisplayName("Deve lançar erro se o coidigo for duplicado - exemplo de regra de negoicio")
    public void createVisitaWithDuplicatedId() throws Exception {

        VisitaDTO dto = createNewValidVisitaDto();

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
