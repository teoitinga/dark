package com.jp.dark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.ProdutorDTO;
import com.jp.dark.exceptions.PersonaAlreadyExistsException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.factory.CallFactory;
import com.jp.dark.factory.ProdutorFactory;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.services.ProdutorService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ProdutorController.class)
@AutoConfigureMockMvc
public class ProdutorControllerTest {

    static String API = "/api/v1/produtores";

    @Autowired
    MockMvc mvc;

    @MockBean
    private ProdutorService produtorService;

    @MockBean
    private PersonaRepository repository;

    @Test
    @DisplayName("Deve registrar um produtor")
    public void saveTest() throws Exception {

        ProdutorDTO dto = ProdutorFactory.createNewProdutorDto();

        String json = new ObjectMapper().writeValueAsString(dto);

        ProdutorDTO saved = ProdutorFactory.createNewProdutorDto();

        BDDMockito.given(produtorService.save(dto)).willReturn(saved);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("cpf").isNotEmpty())
                .andExpect(jsonPath("nome").value("Fábio Cauã Moreira"))
        ;
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar cadastrar um produtor cujo CPF já existe")
    public void saveWithCPfDuplicatedTest() throws Exception {

        ProdutorDTO dto = ProdutorFactory.createNewProdutorDto();

        String json = new ObjectMapper().writeValueAsString(dto);

        ProdutorDTO saved = ProdutorFactory.createNewProdutorDto();

        BDDMockito.given(produtorService.save(dto)).willThrow(new PersonaAlreadyExistsException());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0]")
                        .value("Persona already exists and cannot be overwritten"))
        ;
    }
}
