package com.github.bcopy.revealing.process.fs.simple;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import com.github.bcopy.revealing.model.Cursor;
import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.process.fs.AbstractFileVisitor;

/**
 *  This file visitor must be the first to run, as it extracts
 *  essential information from the file system to set item contents.
 *  It is also using commonly available attributes, such as creation and modification dates. 
 *  As a Spring component bean, it is marked as highest precedence so it always runs first.
 */
public class SimpleFileSystemVisitor extends AbstractFileVisitor {
	
	public SimpleFileSystemVisitor(Cursor cursor) {
		super(cursor);
	}

	@Override
	public FileVisitResult onNewItem(Item item, Path path, BasicFileAttributes fileAttr) {
		item.setAbsolutePath(path.toString());
		item.setRelativePath(path.getFileName().toString());
		item.setCreated(fileAttr.creationTime().toMillis());
		item.setModified(fileAttr.lastModifiedTime().toMillis());

		return FileVisitResult.CONTINUE;
	}

}