package com.jp.dark.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.dark.dtos.CallDTO;
import com.jp.dark.dtos.CallDTOPost;
import com.jp.dark.factory.CallFactory;
import com.jp.dark.factory.VisitaFactory;
import com.jp.dark.models.entities.Call;
import com.jp.dark.models.entities.Visita;
import com.jp.dark.models.repository.CallRepository;
import com.jp.dark.security.AuthenticationService;
import com.jp.dark.security.jwt.JwtService;
import com.jp.dark.services.CallService;
import com.jp.dark.services.VisitaService;
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

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
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

    @MockBean
    CallRepository repository;

    @Qualifier("userDetailsServiceImpl")
    @MockBean
    AuthenticationService userDetailsService;

    @MockBean
    JwtService jwtService;

    @MockBean
    PasswordEncoder encoder;

    @Test
    @DisplayName("Deve realizar uma chamada de serviço")
    @WithMockUser(username = "04459471604", roles = {"CEDIDO", "TECNICO"})
    public void saveTest() throws Exception {

        CallDTOPost dto = CallFactory.createOtherCallDTOPost();
        String json = new ObjectMapper().writeValueAsString(dto);

        BDDMockito.given(visitaService.getByCodigo(Mockito.anyString()))
                .willReturn(VisitaFactory.createSavedVisitaDto());

        CallDTOPost savedCall = CallFactory.createAnyCallDTOPost();
        Visita vs = VisitaFactory.createVisitaEntity();
        BDDMockito.given(callService.save(dto)).willReturn(savedCall);
        BDDMockito.given(visitaService.getByCodigo(Mockito.anyString())).willReturn(VisitaFactory.createVisitaDto());
        BDDMockito.given(repository.save(Mockito.any(Call.class))).willReturn(CallFactory.createCall());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isForbidden())
                ;
    }

}
