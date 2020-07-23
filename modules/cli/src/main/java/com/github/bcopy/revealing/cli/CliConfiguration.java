package com.github.bcopy.revealing.cli;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CliConfiguration {

	@Bean
	@ConfigurationProperties("revealing.cli")
	CliConfigurationProperties properties() {
		return new CliConfigurationProperties();
	}

}
