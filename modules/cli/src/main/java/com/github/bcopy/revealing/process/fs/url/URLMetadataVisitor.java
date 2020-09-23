package com.github.bcopy.revealing.process.fs.url;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.bcopy.revealing.model.Cursor;
import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.process.fs.AbstractFileVisitor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class URLMetadataVisitor extends AbstractFileVisitor {
	
	public URLMetadataVisitor(Cursor cursor) {
		super(cursor);
	}

	@Override
	public FileVisitResult onNewItem(Item item, Path path, BasicFileAttributes fileAttr) {
		item.setCaption(item.getTitle());
		Map<String, String> metadata = new HashMap<>();
		
		try {
			metadata.put("url", Files.readString(path));
			item.setMetadata(metadata);
		} catch (IOException e) {
			log.error("Could not parse URL file {} : {}", path, e);
		}
		return null;
	}

	@Override
	public FileVisitResult acceptNewItem(Path path, BasicFileAttributes fileAttr, String title, String mimeType) {
		Optional<String> ext = getExtensionByStringHandling(path.getFileName().toString());
		if (ext.isPresent() && "url".equalsIgnoreCase(ext.get())) {
			return FileVisitResult.CONTINUE;
		}
		return FileVisitResult.TERMINATE;
	}
	
}