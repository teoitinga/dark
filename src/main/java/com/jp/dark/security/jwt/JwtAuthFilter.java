package com.jp.dark.security.jwt;

import com.jp.dark.models.entities.Persona;
import com.jp.dark.models.repository.PersonaRepository;
import com.jp.dark.security.AuthenticationService;
import com.jp.dark.services.PersonaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {


    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    private JwtService jwtService;
    private UserDetailsService usuarioService;
    private PersonaService personaService;

    private JwtService service;

    private AuthenticationService UserDetailsServiceImpl;

    public JwtAuthFilter(JwtService jwtService, AuthenticationService userDetailsService) {
        this.service = jwtService;
        this.UserDetailsServiceImpl = userDetailsService;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if( authorization!=null && authorization.startsWith("Bearer") ) {
            String token = authorization.split(" ")[1];
            boolean isValid = service.tokenValido(token);

            if(isValid) {
                String usuarioLogado = service.obterLogin(token);
                Persona usuario = UserDetailsServiceImpl.loadUserByUsername(usuarioLogado);
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}
