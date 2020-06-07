package com.github.bcopy.revealing.process.fs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.github.bcopy.revealing.model.Slideshow;
import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

class FileSystemProcessorTest {

	@Test
	void testSimpleFilesystem() throws IOException {
		FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
		
	    Path rootPath = fs.getPath("/home", "user1", "slideshows");
	    Files.createDirectories(rootPath);
	    for (int i = 1; i <= 5; i++) {
	    	Path path = rootPath.resolve("slideshow"+i);
			Files.createDirectory(path);
			Files.write(path.resolve("image"+i+"-1.jpg"),ImmutableList.of("contents"), StandardCharsets.UTF_8);
			Files.write(path.resolve("image"+i+"-2.jpg"),ImmutableList.of("contents"), StandardCharsets.UTF_8);
			Files.write(path.resolve("image"+i+"-3.jpg"),ImmutableList.of("contents"), StandardCharsets.UTF_8);
		}
		
		FileSystemProcessor fsp = new FileSystemProcessor();
		Slideshow slideshow = fsp.process(rootPath);
		
		assertTrue(slideshow.getCategories().size()==5);
	    
	}
	

}
