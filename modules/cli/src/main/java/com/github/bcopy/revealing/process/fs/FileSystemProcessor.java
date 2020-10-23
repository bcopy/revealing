package com.github.bcopy.revealing.process.fs;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.bcopy.revealing.model.Cursor;
import com.github.bcopy.revealing.process.Processor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileSystemProcessor implements Processor<Path> {
	
	List<FileVisitorFactory<AbstractFileVisitor>> visitorFactories;
	
	public FileSystemProcessor(@Autowired List<FileVisitorFactory<AbstractFileVisitor>> visitorFactories) {
		this.visitorFactories = visitorFactories;
	}
	
	@Override
	public Cursor process(Cursor cursor, Path... paths) {
		
		for (Path path : paths) {
			String slideshowName = path.getFileName().toString();
			
			cursor.setOrCreateSlideshow(slideshowName);

			for (FileVisitorFactory<AbstractFileVisitor> visitorFactory : visitorFactories) {
				FileVisitor<Path> visitor = visitorFactory.getInstance(cursor);
				try {
					Files.walkFileTree(path, visitor);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		
		return cursor;
	}
}
