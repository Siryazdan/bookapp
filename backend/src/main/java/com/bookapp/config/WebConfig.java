package com.bookapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // ✅ ALL endpoints
                        .allowedOrigins("http://localhost:5175", "http://localhost:5176") // ✅ Your frontend ports
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ MUST include OPTIONS
                        .allowedHeaders("*") // ✅ Accept all headers including Authorization
                        .exposedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
