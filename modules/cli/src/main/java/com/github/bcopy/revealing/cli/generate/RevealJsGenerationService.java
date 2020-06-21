package com.github.bcopy.revealing.cli.generate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.bcopy.revealing.model.Slideshow;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;

@Component
public class RevealJsGenerationService {

	@Autowired
	private
	JadeConfiguration jadeConfiguration;
	
	
	@Autowired
	private
	GenerationConfigurationProperties properties;
	
	
	public void persistSlideshows(Path persistenceRootPath, String templateName, Slideshow... slideshows) throws IOException {
		JadeTemplate template = getJadeConfiguration().getTemplate(templateName);
		
		for(Slideshow slideshow : slideshows) {
			Map<String, Object> model = new HashMap<>();
			model.put("slideshow", slideshow);
			String slideshowHtml = getJadeConfiguration().renderTemplate(template, model);

			Path destinationPath = persistenceRootPath.resolve(slideshow.getName());
			Files.createDirectories(destinationPath);
			Path outputPath = destinationPath.resolve("index.html");
			
			Files.createFile(outputPath);
			Files.writeString(outputPath, slideshowHtml, StandardCharsets.UTF_8);
		}
	}


	public JadeConfiguration getJadeConfiguration() {
		return jadeConfiguration;
	}


	public void setJadeConfiguration(JadeConfiguration jadeConfiguration) {
		this.jadeConfiguration = jadeConfiguration;
	}


	public GenerationConfigurationProperties getProperties() {
		return properties;
	}


	public void setProperties(GenerationConfigurationProperties properties) {
		this.properties = properties;
	}

}
