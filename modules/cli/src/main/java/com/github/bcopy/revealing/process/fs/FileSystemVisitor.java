package com.github.bcopy.revealing.process.fs;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import com.github.bcopy.revealing.model.Category;
import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.process.Cursor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemVisitor implements FileVisitor<Path>{
	private Cursor cursor;

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
    	if(cursor.getHierarchyLevel().intValue() > 0) {
    		// Create a new category
        	Category category = new Category();
        	
    		// Push the new category on top of the stack
    		cursor.setCurrentCategory(category);
    		category.setName(path.getFileName().toString());
        	cursor.getCurrentSlideshow().getCategories().add(category);
    	}
    	
    	cursor.getHierarchyLevel().incrementAndGet();
    	
    	return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttr) throws IOException {
        if(fileAttr.isRegularFile()) {
	    	// Create a new item
	    	Item i = new Item();
	    	
	    	cursor.setCurrentItem(i);
	    	
	    	i.setName(path.getFileName().toString());
	    	i.setAbsolutePath(path.toString());
	    	i.setRelativePath(path.getFileName().toString());
	    	i.setCreated(fileAttr.creationTime().toMillis());
	    	i.setModified(fileAttr.lastModifiedTime().toMillis());
	    	
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
