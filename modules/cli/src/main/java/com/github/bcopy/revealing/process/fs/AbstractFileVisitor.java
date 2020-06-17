package com.github.bcopy.revealing.process.fs;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import com.github.bcopy.revealing.model.Category;
import com.github.bcopy.revealing.model.Slideshow;
import com.github.bcopy.revealing.process.Cursor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractFileVisitor implements FileVisitor<Path>, CursorEventListener {
	
	private Cursor cursor;
	
	public AbstractFileVisitor(Cursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path path, IOException arg1) throws IOException {
		getCursor().getHierarchyLevel().decrementAndGet();
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttr) throws IOException {

		// Link parent and child if a category is being assembled.
		if (getCursor().getHierarchyLevel().intValue() > 0) {
			cursor.setOrCreateCategoryInCurrentSlideshow(path.getFileName().toString());
			onNewCategory(cursor.getCurrentCategory(), path, fileAttr);
		}

		cursor.getHierarchyLevel().incrementAndGet();

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttr) throws IOException {
		if (fileAttr.isRegularFile()) {
			// Create a new item
			String title = Utils.capitalizeString(path.getFileName().toString().replaceFirst("[.][^.]+$", "").replace("_", " "));
			cursor.setOrCreateItemInCurrentCategory(title);

			onNewItem(cursor.getCurrentItem(), path, fileAttr);
		}

		return FileVisitResult.CONTINUE;
	}

	
	@Override
	public FileVisitResult visitFileFailed(Path path, IOException exception) throws IOException {
		// What to do in case of failure ?
		log.error("Ignoring file : Could not visit {} : {}", path.toString(), exception.getMessage(), exception);
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult onNewCategory(Category category, Path path, BasicFileAttributes fileAttr) {
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult onNewSlideshow(Slideshow slideshow, Path path, BasicFileAttributes fileAttr) {
		return FileVisitResult.CONTINUE;
	}


	public Cursor getCursor() {
		return cursor;
	}
  }