package com.github.bcopy.revealing.cli;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.bcopy.revealing.cli.generate.RevealJsGenerationService;

@Configuration
public class CliConfiguration {

    @Bean
    @ConfigurationProperties("revealing.cli")
    CliConfigurationProperties properties() {
       return new CliConfigurationProperties();
    }
    
    @Bean
    RevealJsGenerationService generationService() {
        return new RevealJsGenerationService();
    }
}
