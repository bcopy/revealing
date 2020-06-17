package com.github.bcopy.revealing.process.fs;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import com.github.bcopy.revealing.model.Category;
import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.model.Slideshow;

public interface CursorEventListener {

	FileVisitResult onNewItem(Item item, Path path, BasicFileAttributes fileAttr);

	FileVisitResult onNewCategory(Category category, Path path, BasicFileAttributes fileAttr);

	FileVisitResult onNewSlideshow(Slideshow slideshow, Path path, BasicFileAttributes fileAttr);

}