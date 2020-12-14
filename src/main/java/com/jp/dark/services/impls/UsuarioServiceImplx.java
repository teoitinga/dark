package com.jp.dark.services.impls;

import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;


public class UsuarioServiceImplx  {
//
//    @Autowired
//    private PersonaRepository personaRepository;
//
//    @Autowired
//    private PasswordEncoder encoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//
//        Persona usuario = this.personaRepository.findByCpf(login)
//                .orElseThrow(()->new UsernameNotFoundException("Usuario não encontrado!"));
//
//        if(!usuario.getEnabled()){
//           throw new UsernameNotFoundException("Usuario não está habilitado para acessar este sistema!");
//        }
//
//        String[] roles = new String[]{usuario.getPermissao().toString()};
//        log.info("Usuário autenticado: {}", usuario);
//        return User.builder()
//                .username(usuario.getCpf())
//                .password(encoder.encode(usuario.getSenha()))
//                    .roles(roles)
//                .build();
//    }
//    private final static class UserRepositoryUserDetails extends Persona implements UserDetails{
//        public UserRepositoryUserDetails(Persona user) {
//            super(user);
//        }
//
//        @Override
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(1);
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + this.getPermissao().toString()));
//            return authorities;
//        }
//
//        @Override
//        public String getPassword() {
//            return this.getSenha();
//        }
//
//        @Override
//        public String getUsername() {
//            return this.getCpf();
//        }
//
//        @Override
//        public boolean isAccountNonExpired() {
//            return false;
//        }
//
//        @Override
//        public boolean isAccountNonLocked() {
//            return true;
//        }
//
//        @Override
//        public boolean isCredentialsNonExpired() {
//            return true;
//        }
//
//        @Override
//        public boolean isEnabled() {
//            return true;
//        }
//    }
}
