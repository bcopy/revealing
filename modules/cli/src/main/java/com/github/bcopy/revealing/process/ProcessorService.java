package com.github.bcopy.revealing.process;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessorService {
	
	FileSystem filesystem;

	@Autowired
	ProcessorConfigurationProperties configuration;
	
	@Autowired
	private List<Processor<Path>> pathProcessors;
	
	public Cursor processPaths() {
		Cursor cursor = new Cursor();
		for(String pathString : configuration.getContentPaths()) {
			Path path;
			if(filesystem != null) {
				path = filesystem.getPath(pathString);
			}else {
				path = Paths.get(pathString);
			}
			for(Processor<Path> processor : pathProcessors) {
				processor.process(cursor, path);
			}
		}
		return cursor;
	}
	
}
