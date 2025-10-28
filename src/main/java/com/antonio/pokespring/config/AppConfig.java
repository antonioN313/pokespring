package com.antonio.pokespring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@Slf4j
public class AppConfig {
    
    @Bean
    public String appStartupMessage() {
        String message = String.format(
            "🚀 PokeSpring API iniciada com sucesso em %s",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        );
        log.info(message);
        return message;
    }
    
    @Bean
    public AppInfo appInfo() {
        return new AppInfo(
            "PokeSpring",
            "1.0.0",
            "API REST que consome PokeAPI v2 e cacheia dados no H2"
        );
    }
    
    public record AppInfo(String name, String version, String description) {}
}