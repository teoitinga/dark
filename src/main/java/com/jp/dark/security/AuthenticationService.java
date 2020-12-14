package com.jp.dark.security;

import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.security.dto.AutheticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService implements UserDetailsService {

    private PersonaRepository repository;

    public AuthenticationService(PersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Persona usuario = this.repository.findByCpf(cpf)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User not found with name is %s", cpf)));

        return new AutheticatedUser(usuario.getCpf(), usuario.getSenha(), usuario.getPermissao().toString());
    }
}
