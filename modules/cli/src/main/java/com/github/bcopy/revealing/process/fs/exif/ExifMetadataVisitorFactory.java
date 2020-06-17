package com.github.bcopy.revealing.process.fs.exif;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.bcopy.revealing.model.Cursor;
import com.github.bcopy.revealing.process.fs.AbstractFileVisitor;
import com.github.bcopy.revealing.process.fs.FileVisitorFactory;


@ConditionalOnProperty(
		  prefix="revealing.process",
		  name = "visitors",
		  matchIfMissing = true,
		  havingValue = "exif")
@Component
public class ExifMetadataVisitorFactory implements FileVisitorFactory<AbstractFileVisitor> {
	
  public ExifMetadataVisitor getInstance(Cursor cursor){
	  return new ExifMetadataVisitor(cursor);
  }

}
