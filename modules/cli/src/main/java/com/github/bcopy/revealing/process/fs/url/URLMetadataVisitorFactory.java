package com.github.bcopy.revealing.process.fs.url;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.bcopy.revealing.model.Cursor;
import com.github.bcopy.revealing.process.fs.AbstractFileVisitor;
import com.github.bcopy.revealing.process.fs.FileVisitorFactory;


@ConditionalOnProperty(
		  prefix="revealing.process",
		  name = "visitors",
		  matchIfMissing = true,
		  havingValue = "url")
@Component
public class URLMetadataVisitorFactory implements FileVisitorFactory<AbstractFileVisitor> {
	
  public URLMetadataVisitor getInstance(Cursor cursor){
	  return new URLMetadataVisitor(cursor);
  }

}
