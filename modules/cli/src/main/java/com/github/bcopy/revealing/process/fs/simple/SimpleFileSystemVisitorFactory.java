package com.github.bcopy.revealing.process.fs.simple;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.bcopy.revealing.process.Cursor;
import com.github.bcopy.revealing.process.fs.AbstractFileVisitor;
import com.github.bcopy.revealing.process.fs.FileVisitorFactory;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleFileSystemVisitorFactory implements FileVisitorFactory<AbstractFileVisitor> {
	
  public AbstractFileVisitor getInstance(Cursor cursor){
	  return new SimpleFileSystemVisitor(cursor);
  }

}
