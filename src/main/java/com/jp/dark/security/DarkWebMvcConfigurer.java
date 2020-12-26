package com.jp.dark.security;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class DarkWebMvcConfigurer implements WebMvcConfigurer {
    /*
     * @Override public void addCorsMappings(CorsRegistry registry) {
     * registry.addMapping("/**") .allowedMethods("*"); }
     */

    @Override public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
        ;
    }
}
