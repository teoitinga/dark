package com.jp.dark.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.CallDTO;
import com.jp.dark.factory.CallFactory;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.services.CallService;
import com.jp.dark.services.VisitaService;
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

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = CallController.class)
@AutoConfigureMockMvc
public class CallControllerTest {

    static String API = "/api/v1/chamadas";

    @Autowired
    MockMvc mvc;

    @MockBean
    private VisitaService visitaService;

    @MockBean
    private CallService callService;

    @Test
    @DisplayName("Deve realizar uma chamada de serviço")
    public void saveTest() throws Exception {
        CallDTO dto = CallFactory.createNewCallDto();
        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(visitaService.getByCodigo(Mockito.anyString()))
                .willReturn(Optional.of(VisitaFactory.createSavedVisitaDto()));

        CallDTO savedCall = CallFactory.createSavedCallDto();

        BDDMockito.given(callService.save(dto)).willReturn(savedCall);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("codigo").isNotEmpty())
                ;
    }

}