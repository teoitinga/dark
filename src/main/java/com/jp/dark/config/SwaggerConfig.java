package com.jp.dark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
        .apiInfo(this.apiInfo());
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
