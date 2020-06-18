package com.github.bcopy.revealing.cli;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.bcopy.revealing.cli.generate.RevealJsGenerationService;

import de.neuland.jade4j.JadeConfiguration;

@Configuration
public class CliConfiguration {

    @Bean
    @ConfigurationProperties("revealing.cli")
    CliConfigurationProperties properties() {
       return new CliConfigurationProperties();
    }
    
    @Bean
    JadeConfiguration jadeConfiguration(CliConfigurationProperties properties) {
        JadeConfiguration jadeConfiguration = new JadeConfiguration();
        jadeConfiguration.setPrettyPrint(properties.getPrettyPrint());
        
        return jadeConfiguration;
    }
    
    @Bean
    RevealJsGenerationService generationService(CliConfigurationProperties properties, JadeConfiguration jadeConfiguration) {
        RevealJsGenerationService service = new RevealJsGenerationService(jadeConfiguration);
        
        return service;
    }
}
