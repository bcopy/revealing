package com.github.bcopy.revealing.process.fs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.model.Slideshow;
import com.github.bcopy.revealing.process.Cursor;
import com.github.bcopy.revealing.process.fs.exif.ExifMetadataVisitorFactory;
import com.github.bcopy.revealing.process.fs.simple.SimpleFileSystemVisitorFactory;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

class FileSystemProcessorTest {

	@Test
	void testSimpleFilesystemProcessor() throws IOException {
		Path rootPath = initializeTestFileSystem();
		
	    FileSystemProcessor fsp = new FileSystemProcessor(Arrays.asList(new SimpleFileSystemVisitorFactory()));
		Cursor c = new Cursor();
		Slideshow slideshow = fsp.process(c, rootPath).getSlideshows().get(0);
		
		assertEquals(5, slideshow.getCategories().size());
		Item redItem = slideshow.getCategories().get("slideshow1").getItems().get("Red1");
		
		assertTrue(redItem != null);
		assertNull(redItem.getCaption());
	}

	@Test
	void testExifFilesystemProcessor() throws IOException {
		Path rootPath = initializeTestFileSystem();
		
	    FileSystemProcessor fsp = new FileSystemProcessor(Arrays.asList(new ExifMetadataVisitorFactory()));
		Cursor c = new Cursor();
		Slideshow slideshow = fsp.process(c, rootPath).getSlideshows().get(0);
		
		assertEquals(5, slideshow.getCategories().size());
		Item redItem = slideshow.getCategories().get("slideshow1").getItems().get("Red1");
		
		assertTrue(redItem != null);
		assertEquals("Red", redItem.getCaption());
		
	}


	
	private static final Path initializeTestFileSystem() throws IOException {
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
				} catch (IOException e) {
					fail("Could not copy test image "+color);
				}
			});
		}
		return rootPath;
	}
	

}
