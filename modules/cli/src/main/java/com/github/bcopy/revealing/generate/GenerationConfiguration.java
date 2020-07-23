package com.github.bcopy.revealing.generate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.ClasspathTemplateLoader;
import de.neuland.jade4j.template.FileTemplateLoader;

@Configuration
public class GenerationConfiguration {
	@Bean
	@ConfigurationProperties("revealing.generate")
	GenerationConfigurationProperties generationConfigurationProperties() {
		return GenerationConfigurationProperties.builder().build();
	}

	@Bean
	JadeConfiguration jadeConfiguration(GenerationConfigurationProperties properties) {
		JadeConfiguration jadeConfiguration = new JadeConfiguration();
		jadeConfiguration.setPrettyPrint(properties.getPrettyPrint());
		switch (properties.locator) {
		case FILESYSTEM:
			jadeConfiguration
					.setTemplateLoader(new FileTemplateLoader(properties.getTemplatePath(), properties.getEncoding()));
			break;
		case CLASSPATH:
		default:
			jadeConfiguration.setTemplateLoader(new ClasspathTemplateLoader());
		}
		return jadeConfiguration;
	}

}
