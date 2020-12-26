package com.jp.dark.security;

import com.jp.dark.dtos.CredenciaisDTO;
import com.jp.dark.dtos.TokenDTO;
import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.security.dto.AutheticatedUser;
import com.jp.dark.security.exceptions.InvaliPasswordException;
import com.jp.dark.security.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsServiceImpl")
@Slf4j
public class AuthenticationService implements UserDetailsService {

    private PersonaRepository repository;
    private PasswordEncoder encoder;
    private JwtService jwtService;

    public AuthenticationService(PersonaRepository repository,
                                 PasswordEncoder encoder,
                                 JwtService jwtService
    ) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Override
    public Persona loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Persona usuario = this.repository.findByCpf(cpf)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User not found with name is %s", cpf)));
        return usuario;
    }

    public TokenDTO autenticar(CredenciaisDTO credenciais) {
        Persona usuario = this.autenticarUsuario(credenciais);

        String token = jwtService.geraToken(usuario);
        return TokenDTO.builder()
                .login(usuario.getUsername())
                .role(usuario.getPermissao().toString())
                .token(token)
                .build();

    }
    public Persona autenticarUsuario(CredenciaisDTO credenciais){
        Persona usuario = this.loadUserByUsername(credenciais.getLogin());

        boolean isAproved = encoder.matches(credenciais.getSenha(), usuario.getPassword());

        if(isAproved){
            return usuario;
        }
        throw new InvaliPasswordException();
    }
}
