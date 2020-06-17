package com.github.bcopy.revealing.process.fs;

import com.github.bcopy.revealing.process.Cursor;

public interface FileVisitorFactory<T extends AbstractFileVisitor> {
	public T getInstance(Cursor cursor);
}
