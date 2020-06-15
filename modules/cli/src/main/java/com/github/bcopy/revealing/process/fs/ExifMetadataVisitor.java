package com.github.bcopy.revealing.process.fs;

import static com.drew.metadata.exif.ExifDirectoryBase.TAG_DATETIME_ORIGINAL;
import static com.drew.metadata.exif.ExifDirectoryBase.TAG_IMAGE_DESCRIPTION;
import static com.drew.metadata.exif.ExifDirectoryBase.TAG_USER_COMMENT;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.github.bcopy.revealing.model.Category;
import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.process.Cursor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(
		  prefix="revealing.process",
		  name = "visitors",
		  matchIfMissing = true,
		  havingValue = "exif")
@Component
public class ExifMetadataVisitor implements FileVisitor<Path> {
	
	private Cursor cursor;

	Iterable<JpegSegmentMetadataReader> metadataReaders = Arrays.asList(new ExifReader());

	@Override
	public FileVisitResult postVisitDirectory(Path path, IOException arg1) throws IOException {
		getCursor().getHierarchyLevel().decrementAndGet();
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes arg1) throws IOException {

		// Link parent and child if a category is being assembled.
		if (getCursor().getHierarchyLevel().intValue() > 0) {
			// Create a new category
			Category category = new Category();

			// Push the new category on top of the stack
			getCursor().setCurrentCategory(category);
			category.setTitle(path.getFileName().toString());
			getCursor().getCurrentSlideshow().getCategories().add(category);
		}

		getCursor().getHierarchyLevel().incrementAndGet();

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttr) throws IOException {
		if (fileAttr.isRegularFile()) {
			// Create a new item
			Item i = new Item();

			getCursor().setCurrentItem(i);

			i.setTitle(capitalizeString(path.getFileName().toString().replaceFirst("[.][^.]+$", "").replace("_", " ")));
			i.setAbsolutePath(path.toString());
			i.setRelativePath(path.getFileName().toString());
			i.setCreated(fileAttr.creationTime().toMillis());
			i.setModified(fileAttr.lastModifiedTime().toMillis());

			extractExifMetadata(path, i);

			getCursor().getCurrentCategory().getItems().add(i);
		}

		return FileVisitResult.CONTINUE;
	}

	private void extractExifMetadata(Path path, Item i) {
		try {
			Metadata metadata = JpegMetadataReader.readMetadata(Files.newInputStream(path), metadataReaders);
			ExifIFD0Directory exifId0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
			
			// Fallback on the item title, if any is set
			i.setCaption(i.getTitle());
			
			if(exifSubIFDDirectory != null) {
				if(exifSubIFDDirectory.containsTag(TAG_DATETIME_ORIGINAL)) {
				  i.setCreated(exifSubIFDDirectory.getDate(TAG_DATETIME_ORIGINAL).getTime());
				}
				if(exifSubIFDDirectory.containsTag(TAG_USER_COMMENT)) {
				  i.setCaption(exifSubIFDDirectory.getString(TAG_USER_COMMENT));
				}
			}
			if(exifId0Directory != null && exifId0Directory.containsTag(TAG_IMAGE_DESCRIPTION)) {
				i.setCaption(exifId0Directory.getString(TAG_IMAGE_DESCRIPTION));
			}
			
			if(exifSubIFDDirectory == null && exifId0Directory == null) {
				log.info("No EXIF information : Could not extract metadata from '{}'", path.toString());
			}
		} catch (JpegProcessingException | IOException ex) {
			if (log.isWarnEnabled()) {
				log.warn("Could not extract metadata from '{}'", path.toString(), ex);
			}
		}
	}

	@Override
	public FileVisitResult visitFileFailed(Path path, IOException exception) throws IOException {
		// What to do in case of failure ?
		log.error("Ignoring file : Could not visit {} : {}", path.toString(), exception.getMessage(), exception);
		return FileVisitResult.CONTINUE;
	}
	
	private static final String capitalizeString(String string) {
		  char[] chars = string.toLowerCase().toCharArray();
		  boolean found = false;
		  for (int i = 0; i < chars.length; i++) {
		    if (!found && Character.isLetter(chars[i])) {
		      chars[i] = Character.toUpperCase(chars[i]);
		      found = true;
		    } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
		      found = false;
		    }
		  }
		  return String.valueOf(chars);
		}

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

}
