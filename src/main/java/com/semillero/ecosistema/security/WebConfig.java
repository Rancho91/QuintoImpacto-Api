package com.semillero.ecosistema.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${WEB_CONFIG_ORIGINS_LOCALHOST}")
    private String localhost;

    @Value("${WEB_CONFIG_ORIGINS_OPTIONAL}")
    private String optional;

    @Value("${WEB_CONFIG_ORIGINS_LOCALHOST_DOS}")
   private String localhostDos;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")

                .allowedOrigins(localhost, localhostDos, optional)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}