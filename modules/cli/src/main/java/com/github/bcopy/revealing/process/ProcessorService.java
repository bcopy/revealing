package com.github.bcopy.revealing.process;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.bcopy.revealing.model.Cursor;
import com.github.bcopy.revealing.model.Slideshow;
import com.github.bcopy.revealing.process.fs.FileSystemProcessor;

public class ProcessorService {
	
	FileSystem filesystem;

	@Autowired
	ProcessorConfigurationProperties configuration;
	
	@Autowired
	FileSystemProcessor fileSystemProcessor;
	
	
	public List<Slideshow> processConfiguredPaths() {
		Cursor cursor = new Cursor();
		for(String pathString : configuration.getContentPaths()) {
			Path path;
			if(filesystem != null) {
				path = filesystem.getPath(pathString);
			}else {
				path = Paths.get(pathString);
			}
			fileSystemProcessor.process(cursor, path);
		}
		return cursor.getSlideshows();
	}
	
}
