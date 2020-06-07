package com.github.bcopy.revealing.process.fs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.bcopy.revealing.model.Slideshow;
import com.github.bcopy.revealing.process.Cursor;
import com.github.bcopy.revealing.process.Processor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemProcessor implements Processor<Path> {
	Cursor cursor;

	FileSystemVisitor visitor;

	@Override
	public Slideshow process(Path path) {
		cursor = new Cursor();
		
		Slideshow slideshow = new Slideshow();
		cursor.setCurrentSlideshow(slideshow);
		cursor.getSlideshows().add(slideshow);
		slideshow.setName(path.getFileName().toString());

		visitor = new FileSystemVisitor(cursor);
		try {
			Files.walkFileTree(path, visitor);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return slideshow;
	}
}
