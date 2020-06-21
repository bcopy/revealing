package com.github.bcopy.revealing.cli.generate;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.github.bcopy.revealing.cli.CliConfigurationProperties;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;

public class GenerationConfiguration {
	@Bean
	@ConfigurationProperties("revealing.generate")
	GenerationConfigurationProperties generationConfigurationProperties() {
		return new GenerationConfigurationProperties();
	}
	
    @Bean
    JadeConfiguration jadeConfiguration(GenerationConfigurationProperties properties) {
        JadeConfiguration jadeConfiguration = new JadeConfiguration();
        jadeConfiguration.setPrettyPrint(properties.getPrettyPrint());
        jadeConfiguration.setTemplateLoader(new FileTemplateLoader(properties.getTemplatePath(), properties.getEncoding()));
        return jadeConfiguration;
    }
    

}
