package com.github.bcopy.revealing.process;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ProcessorAutoConfiguration {
   
   @Bean
   @ConfigurationProperties("revealing.process")
   ProcessorConfigurationProperties processorConfiguration() {
	   return new ProcessorConfigurationProperties();
   }
   
}
