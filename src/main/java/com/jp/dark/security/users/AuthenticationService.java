package com.jp.dark.security.users;

import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.security.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

    private PersonaRepository repository;

    public AuthenticationService(PersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Persona usuario = repository.findByCpf(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User not found with username %s", username)));

        return AuthenticatedUser.builder()
                .userName(usuario.getCpf())
                .nomeDoUsuario(usuario.getNome())
                .role(usuario.getPermissao().toString())
                .password(usuario.getSenha())
                .build();
    }
}
