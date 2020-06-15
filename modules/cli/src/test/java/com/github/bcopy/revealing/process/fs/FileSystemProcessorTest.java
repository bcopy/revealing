package com.github.bcopy.revealing.process.fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.model.Slideshow;
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
			
			final int index = i;
			Arrays.asList("blue","red","white","black").stream().forEach(color ->{
				try {
					Files.copy(FileSystemProcessorTest.class.getResourceAsStream("/"+color+".jpg"), path.resolve(color+index+".jpg"), StandardCopyOption.REPLACE_EXISTING);
//					System.out.println(is.markSupported());
				} catch (IOException e) {
					fail("Could not copy test image "+color);
				}
			});
		}
		
		FileSystemProcessor fsp = new FileSystemProcessor();
		Slideshow slideshow = fsp.process(rootPath);
		
		assertEquals(5, slideshow.getCategories().size());
		Optional<Item> redItem = slideshow.getCategories().get(0).getItems().stream().filter(item -> item.getTitle().equals("Red1")).findFirst();
		
		assertTrue(redItem.isPresent());
		assertEquals("Red", redItem.get().getCaption());
	    
	}
	

}
