package com.github.bcopy.revealing.process.fs;

import java.nio.file.FileVisitor;
import java.nio.file.Path;

import com.github.bcopy.revealing.process.Cursor;

public interface FileVisitorFactory {
	public FileVisitor<Path> getInstance(Cursor cursor);
}
