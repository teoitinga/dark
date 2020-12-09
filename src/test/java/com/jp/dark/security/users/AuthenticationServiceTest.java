package com.jp.dark.security.users;

import com.jp.dark.factory.PersonaFactory;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @MockBean
    PersonaRepository repository;

    //@InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup(){
        this.authenticationService = new AuthenticationService(repository);
    }

    @Test
    @DisplayName("Verifica se o usuário foi logado com sucesso.")
    void whenUserNameIsInformedThenUserShouldBeReturned() {

        Persona usuarioExpected = PersonaFactory.createValidPersona();
        SimpleGrantedAuthority expectedUserRole = new SimpleGrantedAuthority("ROLE_" + usuarioExpected.getPermissao());
        String expectedUserName = usuarioExpected.getCpf();

        Mockito.when(repository.findByCpf(expectedUserName)).thenReturn(Optional.of(PersonaFactory.createValidPersona()));

        UserDetails userDetails = authenticationService.loadUserByUsername(expectedUserName);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(usuarioExpected.getCpf());
        assertThat(userDetails.getAuthorities().contains(expectedUserRole));
    }

    @Test
    @DisplayName("retorna erro em caso de usuario não registrado.")
    void whenInvalidUserNameIsInformedThenAnExceptionShouldBeThrown() {

        Persona usuarioExpected = PersonaFactory.createValidPersona();
        SimpleGrantedAuthority expectedUserRole = new SimpleGrantedAuthority("ROLE_" + usuarioExpected.getPermissao());
        String expectedUserName = usuarioExpected.getCpf();

        Mockito.when(repository.findByCpf(expectedUserName)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, ()->authenticationService.loadUserByUsername(expectedUserName));

    }
}
