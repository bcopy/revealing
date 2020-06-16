package com.github.bcopy.revealing.process;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ProcessorAutoConfiguration implements ApplicationContextAware {

   ApplicationContext applicationContext;
   
   @Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
   
   @Bean
   @ConfigurationProperties("revealing.process")
   ProcessorConfigurationProperties processorConfiguration() {
	   return new ProcessorConfigurationProperties();
   }
   
   Map<String, Processor> getProcessorBeans(){
	  return applicationContext.getBeansOfType(Processor.class);
   }
}
