package com.jp.dark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.config.Config;
import com.jp.dark.dtos.VisitaDTO;
import com.jp.dark.exceptions.BusinessException;
import com.jp.dark.exceptions.VisitaNotFoundException;
import com.jp.dark.factory.ProdutorFactory;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.models.repository.VisitaRepository;
import com.jp.dark.models.repository.ServiceProvidedRepository;
import com.jp.dark.services.VisitaService;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = VisitaController.class)
@AutoConfigureMockMvc
public class VisitaControllerTest {

    static String API = "/api/v1/visitas";

    @Autowired
    MockMvc mvc;

    @MockBean
    VisitaService service;

    @MockBean
    VisitaRepository visitaRepository;

    @MockBean
    CallRepository callRepository;

    @MockBean
    PersonaRepository personaRepository;

    @MockBean
    ServiceProvidedRepository serviceProvidedRepository;

    @MockBean
    private Config config;

    @BeforeEach
    public void setUp(){
        //this.service = new VisitaServiceImpl(visitaRepository, callRepository, personaRepository, serviceProvidedRepository, config);
    }
    @Test
    @DisplayName("Deve criar uma visita com sucesso.")
    public void createVisitaTest() throws Exception{

        VisitaDTO dto =  VisitaFactory.createNewValidVisitaDto();

        VisitaDTO savedDto =  VisitaFactory.createNewValidVisitaDto();

        BDDMockito.given(service.save(dto)).willReturn(savedDto);
        BDDMockito.given(service.verifyProdutores(Mockito.anyList())).willReturn(ProdutorFactory.createList5ValidPersona());

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("recomendacao").value(dto.getRecomendacao()))
                .andExpect(jsonPath("situacaoAtual").value(dto.getSituacaoAtual()))
                .andExpect(jsonPath("produtores", hasSize(5)))
                .andExpect(jsonPath("chamadas ", hasSize(3)))
                ;
    }

    @Test
    @DisplayName("Deve filtrar as visitas")
    public void findVisitaTest() throws Exception {
        String codigo = "20201104";

        VisitaDTO visita = VisitaFactory.createNewValidVisitaDto();

        BDDMockito.given(service.find(Mockito.any(VisitaDTO.class), Mockito.any(Pageable.class)))
                .willReturn(new PageImpl<VisitaDTO>(Arrays.asList(visita), PageRequest.of(0,100), 1));

        String queryString = String.format("?situacao=%s&page=0&size=100", visita.getSituacaoAtual());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(1)))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(100))
                .andExpect(jsonPath("pageable.pageNumber").value(0))
                ;
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
                .andExpect(jsonPath("errors", hasSize(4)))
                ;
    }

    @Test
    @DisplayName("Deve obter informações da visita")
    public void getDetailsVisitaTest() throws Exception {
        String codigo = "20201104";

        VisitaDTO visita =  VisitaFactory.createNewValidVisitaDto();
        visita.setCodigoVisita(codigo);


        BDDMockito.when(service.getByCodigo(codigo)).thenReturn(visita);

        //execução
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/" + codigo))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("codigoVisita").isNotEmpty())
                .andExpect(jsonPath("recomendacao").value(visita.getRecomendacao()))
                .andExpect(jsonPath("situacaoAtual").value(visita.getSituacaoAtual()))
                ;
    }
    @Test
    @DisplayName("Deve retornar VisitaNotFoundException ao obter informações da visita que não existe.")
    public void getDetailsVisitaNotFoundTest() throws Exception {
        String codigo = "20201104";

        VisitaDTO visita =  VisitaFactory.createNewValidVisitaDto();
        visita.setCodigoVisita(codigo);

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
