package com.github.bcopy.revealing.process.fs;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import static com.drew.metadata.exif.ExifSubIFDDirectory.*;
import static com.drew.metadata.jpeg.JpegDirectory.*;

import com.github.bcopy.revealing.model.Category;
import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.process.Cursor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemVisitor implements FileVisitor<Path> {
	private Cursor cursor;

	Iterable<JpegSegmentMetadataReader> metadataReaders = Arrays.asList(new ExifReader());

	public FileSystemVisitor(Cursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path path, IOException arg1) throws IOException {
		cursor.getHierarchyLevel().decrementAndGet();
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes arg1) throws IOException {

		// Link parent and child if a category is being assembled.
		if (cursor.getHierarchyLevel().intValue() > 0) {
			// Create a new category
			Category category = new Category();

			// Push the new category on top of the stack
			cursor.setCurrentCategory(category);
			category.setTitle(path.getFileName().toString());
			cursor.getCurrentSlideshow().getCategories().add(category);
		}

		cursor.getHierarchyLevel().incrementAndGet();

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttr) throws IOException {
		if (fileAttr.isRegularFile()) {
			// Create a new item
			Item i = new Item();

			cursor.setCurrentItem(i);

			i.setTitle(path.getFileName().toString().replace("_", " "));
			i.setAbsolutePath(path.toString());
			i.setRelativePath(path.getFileName().toString());
			i.setCreated(fileAttr.creationTime().toMillis());
			i.setModified(fileAttr.lastModifiedTime().toMillis());

			try {
				Metadata metadata = JpegMetadataReader.readMetadata(path.toFile(), metadataReaders);
				ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
				i.setCreated(directory.getDate(TAG_DATETIME_ORIGINAL).getTime());
				String caption = Arrays
						.asList(directory.getString(TAG_USER_COMMENT), directory.getString(TAG_IMAGE_DESCRIPTION))
						.stream()
						.filter(s -> s != null && (!s.isEmpty()))
						.findFirst()
						.orElse(i.getTitle());
				i.setCaption(caption);
			} catch (JpegProcessingException | IOException ex) {
				if (log.isWarnEnabled()) {
					log.warn("Could not extract metadata from '{}'", path.toString(), ex);
				}

			}

			cursor.getCurrentCategory().getItems().add(i);
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path path, IOException exception) throws IOException {
		// What to do in case of failure ?
		log.error("Ignoring file : Could not visit {} : {}", path.toString(), exception.getMessage(), exception);
		return FileVisitResult.CONTINUE;
	}

}
