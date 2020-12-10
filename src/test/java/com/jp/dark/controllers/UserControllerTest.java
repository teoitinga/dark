package com.jp.dark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.UserDTO;
import com.jp.dark.factory.UserFactory;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.PersonaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    static String API = "/api/v1/users";

    @Autowired
    MockMvc mvc;

    @MockBean
    PersonaRepository repository;

    @MockBean
    PersonaService service;

    @Test
    void createTest() throws Exception {
        UserDTO dto = UserFactory.createAnyUser();
        UserDTO dtoExpected = UserFactory.createAnyUser();
        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(service.save(dto)).willReturn(dtoExpected);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("login").value(dto.getLogin()))
                .andExpect(jsonPath("name").value(dto.getName()))
                .andExpect(jsonPath("municipio").value(dto.getMunicipio()))
                .andExpect(jsonPath("role").value(dto.getRole()))
                .andExpect(jsonPath("contato").value(dto.getContato()))
        ;
    }
}
