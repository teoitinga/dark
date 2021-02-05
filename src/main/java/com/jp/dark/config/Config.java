package com.jp.dark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class Config {

    @Bean
    public DateTimeFormatter formater(){
        return DateTimeFormatter.ISO_LOCAL_DATE;
    }
    @Bean
    public DateTimeFormatter formaterPatternddMMyyyy(){
        return DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }
}
