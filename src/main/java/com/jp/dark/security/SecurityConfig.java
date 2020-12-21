package com.jp.dark.security;

import com.jp.dark.models.enums.EnumPermissao;
import com.jp.dark.security.config.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private AuthenticationService userDetailsService;

    private static final String VISITAS_URL = "api/v1/visitas/**";
    private static final String CALL_URL = "api/v1/chamadas/**";
    private static final String PRODUTORES_URL = "api/v1/produtores/**";
    private static final String INFO_RENDA_URL = "api/v1/inforenda/**";
    private static final String INFO_PRICE_URL = "api/v1/infoprice/**";
    private static final String PROGRAMAS_URL = "api/v1/programas/**";
    private static final String SERVICES_PROVIDED_URL = "api/v1/services/**";
    private static final String USERS_URL = "api/v1/users/**";

    private static final String TECNICO_TEST_URL = "api/v1/tecnico/**";

    private static final String ROLE_TECNICO = EnumPermissao.TECNICO.toString();
    private static final String ROLE_CEDIDO = EnumPermissao.CEDIDO.toString();
    private static final String ROLE_ADMINISTRADOR = EnumPermissao.ADMINISTRADOR.toString();

    private static final String H2_CONSOLE_URL = "/h2-console/**";
    private static final String SWAGGER_URL = "/swagger-ui.html";
    private static final String[] SWAGGER_RESOURCES = {
            //SWAGGER-UI
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          AuthenticationService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
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
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PROGRAMAS_URL).hasAnyRole(ROLE_ADMINISTRADOR, ROLE_TECNICO)
                .antMatchers(SERVICES_PROVIDED_URL).hasAnyRole(ROLE_ADMINISTRADOR, ROLE_TECNICO)
                .antMatchers(USERS_URL).hasAnyRole(ROLE_ADMINISTRADOR, ROLE_TECNICO)
                .antMatchers(VISITAS_URL).hasAnyRole(ROLE_ADMINISTRADOR, ROLE_CEDIDO, ROLE_CEDIDO)
                .antMatchers(CALL_URL).hasAnyRole(ROLE_ADMINISTRADOR, ROLE_CEDIDO, ROLE_CEDIDO)
                .antMatchers(PRODUTORES_URL).hasAnyRole(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO)
                .antMatchers(INFO_RENDA_URL).hasAnyRole(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO)
                .antMatchers(INFO_PRICE_URL).hasAnyRole(ROLE_ADMINISTRADOR, ROLE_TECNICO, ROLE_CEDIDO)
                .antMatchers(TECNICO_TEST_URL).hasRole(ROLE_TECNICO)
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;
        http.headers().frameOptions().disable();
    }
}
