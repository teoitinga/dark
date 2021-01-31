package com.jp.dark.security;

import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.security.config.JwtAuthenticationEntryPoint;
import com.jp.dark.security.exceptions.CustomAccessDeniedHandler;
import com.jp.dark.security.jwt.JwtAuthFilter;
import com.jp.dark.security.jwt.JwtService;
import com.jp.dark.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.jp.dark.security.exceptions.AcessDeniedException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private AuthenticationService userDetailsService;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private JwtService jwtService;

    private static final String VISITAS_URL = "/api/v1/visitas/**";
    private static final String CALL_URL = "/api/v1/chamadas/**";
    private static final String CALL_CANCEL_URL = "/api/v1/chamadas/cancel/**";
    private static final String PRODUTORES_URL = "/api/v1/produtores/**";
    private static final String INFO_RENDA_URL = "/api/v1/inforenda/**";
    private static final String INFO_PRICE_URL = "/api/v1/infoprice/**";
    private static final String PROGRAMAS_URL = "/api/v1/programas/**";
    private static final String SERVICES_PROVIDED_URL = "/api/v1/services/**";
    private static final String USERS_URL = "/api/v1/users/**";
    private static final String LOGIN_URL = "/api/v1/auth/**";

    private static final String TECNICO_TEST_URL = "api/v1/tecnico/**";

    private static final String ROLE_CEDIDO = EnumPermissao.CEDIDO.toString();
    private static final String ROLE_TECNICO = EnumPermissao.TECNICO.toString();
    private static final String ROLE_COORDENADOR = EnumPermissao.COORDENADOR.toString();
    private static final String ROLE_GERENTE_REGIONAL = EnumPermissao.GERENTE_REGIONAL.toString();
    private static final String ROLE_ADMINISTRADOR = EnumPermissao.ADMINISTRADOR.toString();
    private static final String ROLE_PREFEITURA = EnumPermissao.PREFEITURA.toString();

    private static final String H2_CONSOLE_URL = "/h2-console/**";
    private static final String SWAGGER_URL = "/swagger-ui.html";
    private static final String[] SWAGGER_RESOURCES = {
            //SWAGGER-UI
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };


    public SecurityConfig(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(PROGRAMAS_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO)
                .antMatchers(VISITAS_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO)
                .antMatchers(HttpMethod.GET, SERVICES_PROVIDED_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO)
                .antMatchers(SERVICES_PROVIDED_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO, ROLE_COORDENADOR, ROLE_GERENTE_REGIONAL, ROLE_PREFEITURA)
                .antMatchers(CALL_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO, ROLE_COORDENADOR, ROLE_GERENTE_REGIONAL, ROLE_PREFEITURA)
                .antMatchers(CALL_CANCEL_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO, ROLE_COORDENADOR, ROLE_GERENTE_REGIONAL, ROLE_PREFEITURA)
                .antMatchers(PRODUTORES_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO, ROLE_COORDENADOR, ROLE_GERENTE_REGIONAL, ROLE_PREFEITURA)
                .antMatchers(INFO_RENDA_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO, ROLE_COORDENADOR, ROLE_GERENTE_REGIONAL, ROLE_PREFEITURA)
                .antMatchers(INFO_PRICE_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO, ROLE_COORDENADOR, ROLE_GERENTE_REGIONAL, ROLE_PREFEITURA)
                .antMatchers(USERS_URL).hasAnyAuthority(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO)
                .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(this.jwtService, this.userDetailsService);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(H2_CONSOLE_URL)
                .antMatchers(SWAGGER_URL)
                .antMatchers(SWAGGER_RESOURCES)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }
}
