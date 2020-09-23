package com.github.bcopy.revealing.process.fs;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

import org.apache.tika.Tika;

import com.github.bcopy.revealing.model.Category;
import com.github.bcopy.revealing.model.Cursor;
import com.github.bcopy.revealing.model.Slideshow;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractFileVisitor implements FileVisitor<Path>, CursorEventListener {
	
	private Cursor cursor;
	
	
	private static final Tika tika = new Tika();
	
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
	
	/**
	 * Indicates whether the file visitor will accept the incoming file for processing.
	 * If accepted, a new Item will be created.
	 * @param path
	 * @param fileAttr
	 * @param title
	 * @param mimeType
	 * @return <code>FileVisitResult.CONTINUE</code> if the incoming item is accepted for processing,
	 *         <code>FileVisitResult.
	 */
	public abstract FileVisitResult acceptNewItem(Path path, BasicFileAttributes fileAttr, String title, String mimeType);

	@Override
	public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttr) throws IOException {
		if (fileAttr.isRegularFile()) {
			// Create a new item
			String title = Utils.capitalizeString(path.getFileName().toString().replaceFirst("[.][^.]+$", "").replace("_", " "));
			String mimeType = tika.detect(path);
			FileVisitResult accept = acceptNewItem(path, fileAttr, title, mimeType);
			
			if(accept == FileVisitResult.CONTINUE) {
				cursor.setOrCreateItemInCurrentCategory(title);
				cursor.getCurrentItem().setMimeType(mimeType);
				onNewItem(cursor.getCurrentItem(), path, fileAttr);
			}
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
	
	public final static Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
  }