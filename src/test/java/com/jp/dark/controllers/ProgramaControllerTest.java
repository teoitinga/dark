package com.jp.dark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.MultiplosBeneficiariosDTO;
import com.jp.dark.factory.BeneficiarioFactory;
import com.jp.dark.models.entities.Beneficiario;
import com.jp.dark.models.repository.BeneficiarioRepository;
import com.jp.dark.models.repository.ProgramaRepository;
import com.jp.dark.security.AuthenticationService;
import com.jp.dark.security.jwt.JwtService;
import com.jp.dark.services.PersonaService;
import com.jp.dark.services.ProgramaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
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
@WebMvcTest(controllers = ProgramaController.class)
@AutoConfigureMockMvc
public class ProgramaControllerTest {

    static String API = "/api/v1/programas";

    @Autowired
    MockMvc mvc;

    @MockBean
    private ProgramaService service;

    @MockBean
    private ProgramaRepository repository;

    @MockBean
    private PersonaService personaService;

    @MockBean
    private BeneficiarioRepository beneficiarioRepository;

    @Qualifier("userDetailsServiceImpl")
    @MockBean
    AuthenticationService userDetailsService;

    @MockBean
    JwtService jwtService;

    @MockBean
    PasswordEncoder encoder;

    @Test
    @DisplayName("Deve registrar um ou varios produtores atendidos em um programa municipal.")
    @WithMockUser(username = "04459471604", roles = {"CEDIDO", "TECNICO"})
    public void registerListTest() throws Exception{

        MultiplosBeneficiariosDTO dto = BeneficiarioFactory.createValidBeneficiariosMultiplos();

        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.when(this.service.save(Mockito.any(MultiplosBeneficiariosDTO.class))).thenReturn(dto);
        BDDMockito.when(this.beneficiarioRepository.save(Mockito.any(Beneficiario.class))).thenReturn(BeneficiarioFactory.createBeneficiario());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("beneficiarios", hasSize(2)))
        ;
    }
    @Test
    @DisplayName("Deve retornar erro ao tentar registrar atendimentos sem informar o programa executado")
    @WithMockUser(username = "04459471604", roles = {"CEDIDO", "TECNICO"})
    public void registerListNoProgramTest() throws Exception{

        MultiplosBeneficiariosDTO dto = BeneficiarioFactory.createValidBeneficiariosMultiplosNoProgram();

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
        ;
    }
}
