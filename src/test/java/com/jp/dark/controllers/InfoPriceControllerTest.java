package com.jp.dark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.PricesDTO;
import com.jp.dark.factory.PricesFactory;
import com.jp.dark.services.InfoPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@WebMvcTest(controllers = InfoPriceController.class)
@AutoConfigureMockMvc
public class InfoPriceControllerTest {
    static String API = "/api/v1/infoprice";

    @Autowired
    MockMvc mvc;

    @MockBean
    InfoPriceService service;

    @Test
    @DisplayName("Deve criar registrar um preco de Boi Gordo com sucesso.")
    public void createInfoBoiMagroPriceTest() throws Exception{

        PricesDTO dto = PricesFactory.createPriceBoiMagro();

        String json = new ObjectMapper().writeValueAsString(dto);

        Mockito.when(service.save(Mockito.any(PricesDTO.class))).thenReturn(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
        ;
    }
    @Test
    @DisplayName("Deve criar registrar um preco de vaca magra com sucesso.")
    public void createInfoVacaMagraPriceTest() throws Exception{

        PricesDTO dto = PricesFactory.createPriceVacaMagra();

        String json = new ObjectMapper().writeValueAsString(dto);

        Mockito.when(service.save(Mockito.any(PricesDTO.class))).thenReturn(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
        ;
    }
    @Test
    @DisplayName("Deve criar registrar um preco do litro de leite com sucesso.")
    public void createInfoLitroDeLeitePriceTest() throws Exception{

        PricesDTO dto = PricesFactory.createPriceLitroDeLeite();

        String json = new ObjectMapper().writeValueAsString(dto);

        Mockito.when(service.save(Mockito.any(PricesDTO.class))).thenReturn(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
        ;
    }
    @Test
    @DisplayName("Deve criar registrar um preco do Boi Gordo com sucesso.")
    public void createInfoBoiGordoPriceTest() throws Exception{

        PricesDTO dto = PricesFactory.createPriceBoiGordo();

        String json = new ObjectMapper().writeValueAsString(dto);

        Mockito.when(service.save(Mockito.any(PricesDTO.class))).thenReturn(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
        ;
    }
    @Test
    @DisplayName("Deve retornar erro ao tentar registrar um preço sem informações.")
    public void createInfoPriceNaDataTest() throws Exception{

        PricesDTO dto = PricesFactory.createPriceNoInfo();

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
    @DisplayName("Deve retornar erro ao tentar registrar um preço sem informação de quantidade por unidade.")
    public void createInfoPriceNoQtdPorUnidTest() throws Exception{

        PricesDTO dto = PricesFactory.createPriceNoQtdPorUnid();

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Você deve informar a quantidade, por exemplo: Quantas @ é o animal vendido"))
        ;
    }
}