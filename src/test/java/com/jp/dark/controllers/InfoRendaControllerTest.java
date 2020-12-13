package com.jp.dark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.InfoRendaDTO;
import com.jp.dark.factory.InfoRendaFactory;
import com.jp.dark.models.entities.InfoRenda;
import com.jp.dark.services.InfoRendaService;
import com.jp.dark.services.impls.InfoRendaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = InfoRendaController.class)
@AutoConfigureMockMvc
public class InfoRendaControllerTest {

    static String API = "/api/v1/inforenda";

    @Autowired
    MockMvc mvc;

    @MockBean
    InfoRendaService service;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("Deve criar uma visita com informações de renda com sucesso.")
    public void createVisitaInfoRendaTest() throws Exception{

        InfoRendaDTO dto = InfoRendaFactory.createValidInfoRenda();

        InfoRendaDTO savedInfoRenda = InfoRendaFactory.createSavedInfoRenda();

        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(service.save(Mockito.any(InfoRendaDTO.class))).willReturn(savedInfoRenda);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                ;
    }

}
