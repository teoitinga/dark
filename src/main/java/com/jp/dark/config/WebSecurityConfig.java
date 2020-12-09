package com.jp.dark.config;

import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.security.JwtAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String ROLE_TECNICO = EnumPermissao.TECNICO.toString();
    private final static String ROLE_CEDIDO = EnumPermissao.CEDIDO.toString();
    private final static String ROLE_CLIENTES = EnumPermissao.CLIENTES.toString();
    private final static String ROLE_ADMINISTRADOR = EnumPermissao.ADMINISTRADOR.toString();
    private final static String ROLE_PREFEITURA = EnumPermissao.PREFEITURA.toString();
    private final static String ROLE_COORDENADOR = EnumPermissao.COORDENADOR.toString();
    private final static String ROLE_GERENTE_REGIONAL = EnumPermissao.GERENTE_REGIONAL.toString();

    //Ferramentas de desenvolvimento
    private final static String H2_URL = "/h2-console/**";
    private final static String SWAGGER_URL = "/swagger-ui.html";
    private final static String[] SWAGGER_RESOURCES = {
            //Swagger-ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    //APIS
    private final static String VISITA_API_URL = "/api/v1/visitas";
    private final static String CALL_API_URL = "/api/v1/chamadas";
    private final static String INFO_PRICE_API_URL = "/api/v1/infoprice";
    private final static String PRODUTORES_API_URL = "/api/v1/produtores";
    private final static String PROGRAMAS_API_URL = "/api/v1/programas";
    private final static String SERVICOS_API_URL = "/api/v1/services";


    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers(VISITA_API_URL,H2_URL, SWAGGER_URL).permitAll()
                .antMatchers(VISITA_API_URL, CALL_API_URL,INFO_PRICE_API_URL,PRODUTORES_API_URL).hasAnyRole(ROLE_TECNICO, ROLE_CEDIDO)
                .antMatchers(PROGRAMAS_API_URL, SERVICOS_API_URL).hasAnyRole(ROLE_TECNICO)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_RESOURCES);
    }
}
