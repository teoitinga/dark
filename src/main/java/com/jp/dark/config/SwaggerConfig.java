package com.jp.dark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(){
return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.jp.dark.controllers"))
        .paths(PathSelectors.any())
        .build()
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .apiInfo(this.apiInfo());
    }
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "acessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;
        SecurityReference reference = new SecurityReference("JWT", scopes);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(reference);
        return auths;
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo(){
    return new ApiInfoBuilder()
            .title("DARK")
            .description("API de Sistema para gerenciamento de atividades no esloc")
            .version("1.0")
            .contact(this.contact())
            .build();
    }
    private Contact contact(){
        return  new Contact(
                "João Paulo",
                "https://github.com/teoitinga",
                "teo.itinga@gmail.com");
    }
}
