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

	JadeConfiguration jadeConfiguration;
	
	public RevealJsGenerationService(@Autowired JadeConfiguration jadeConfiguration) {
		this.jadeConfiguration = jadeConfiguration;
	}
	
	public void persistSlideshows(Path persistenceRootPath, Path templatePath, Slideshow... slideshows) throws IOException {
		JadeTemplate template = jadeConfiguration.getTemplate(templatePath.toString());
		
		for(Slideshow slideshow : slideshows) {
			Map<String, Object> model = new HashMap<>();
			model.put("slideshow", slideshow);
			String slideshowHtml = jadeConfiguration.renderTemplate(template, model);

			Path destinationPath = persistenceRootPath.resolve(slideshow.getName());
			Files.createDirectories(destinationPath);
			Path outputPath = destinationPath.resolve("index.html");
			
			Files.createFile(outputPath);
			Files.writeString(outputPath, slideshowHtml, StandardCharsets.UTF_8);
		}
	}

}
