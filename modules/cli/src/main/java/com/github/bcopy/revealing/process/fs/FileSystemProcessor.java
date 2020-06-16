package com.github.bcopy.revealing.process.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.bcopy.revealing.process.Cursor;
import com.github.bcopy.revealing.process.Processor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemProcessor implements Processor<Path> {
	ExifMetadataVisitor visitor;

	@Override
	public Cursor process(Cursor cursor, Path... paths) {
		
		for (Path path : paths) {
			String slideshowName = path.getFileName().toString();
			
			cursor.setOrCreateSlideshow(slideshowName);

			visitor = new ExifMetadataVisitor();
			visitor.setCursor(cursor);
			
			try {
				Files.walkFileTree(path, visitor);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return cursor;
	}
}
