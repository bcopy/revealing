package com.github.bcopy.revealing.cli.generate;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.github.bcopy.revealing.model.Cursor;
import com.google.common.jimfs.Jimfs;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.ClasspathTemplateLoader;

class RevealJsGenerationServiceTest {

	@Test
	void testSimpleGallery() throws IOException, URISyntaxException {
		FileSystem fs = Jimfs.newFileSystem();
		JadeConfiguration configuration = new JadeConfiguration();
//		configuration.setTemplateLoader(new ClasspathTemplateLoader());
		
		Cursor c = new Cursor();
		c.setOrCreateSlideshow("Slideshow1").setOrCreateCategoryInCurrentSlideshow("Category1");
		c.setOrCreateItemInCurrentCategory("Item1");
		c.getCurrentItem().setRelativePath("photos/item1.jpg");
		c.getCurrentItem().setCaption("item 1");
		RevealJsGenerationService service = new RevealJsGenerationService(configuration);
		Path outputPath = fs.getPath("/tmp", "output");
	    Files.createDirectories(outputPath);
		URL templateResource = RevealJsGenerationServiceTest.class.getResource("/test-resource-packages/photo-gallery/index.jade");
		service.persistSlideshows(outputPath, Paths.get(templateResource.toURI()), c.getCurrentSlideshow());
		
		Path outputIndexFile = outputPath.resolve(c.getCurrentSlideshow().getName()).resolve("index.html");
		assertTrue(Files.exists(outputIndexFile));
		assertTrue(new String(Files.readAllBytes(outputIndexFile)).contains("photos/item1.jpg"));
	}

}
