package com.example.kinoxp.CORS;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")     // ✅ tillader ALLE domæner (korrekt måde)
                .allowedMethods("*")            // tillad alle metoder (GET, POST, PUT, DELETE, osv.)
                .allowedHeaders("*")            // tillad alle headers
                .allowCredentials(true)         // tillad cookies / auth headers
                .maxAge(3600);                  // cache preflight i 1 time
    }
}

