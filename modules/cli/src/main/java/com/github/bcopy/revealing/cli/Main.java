package com.github.bcopy.revealing.cli;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.github.bcopy.revealing.generate.GenerationConfigurationProperties;
import com.github.bcopy.revealing.generate.RevealJsGenerationService;
import com.github.bcopy.revealing.model.Slideshow;
import com.github.bcopy.revealing.process.ProcessorService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(proxyBeanMethods = false)
@ComponentScan({"com.github.bcopy.revealing.cli","com.github.bcopy.revealing.process","com.github.bcopy.revealing.generate"})
@Slf4j
public class Main {
	
	@Autowired
	CliConfigurationProperties cliProperties;
	
	@Autowired
	GenerationConfigurationProperties generationProperties;
	
	@Autowired
    ProcessorService processorService;
	
	@Autowired
	RevealJsGenerationService generatorService;
	
	
	private Main() {
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@PostConstruct
	void start() {
		log.info("Starting processing...");
		
		List<Slideshow> slideshows = processorService.processConfiguredPaths();
		
		for (Slideshow slideshow : slideshows) {
				try {
					generatorService.persistSlideshows(Paths.get(generationProperties.getDestination()), slideshow);
				} catch (IOException e) {
					log.error("Error while processing Slideshow {} (id {} ) ", slideshow.getName(), slideshow.getId(), e.getMessage());
					e.printStackTrace();
				}
		}
		
	}
	
}
