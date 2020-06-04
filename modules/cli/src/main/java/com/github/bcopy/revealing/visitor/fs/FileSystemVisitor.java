package com.github.bcopy.revealing.visitor.fs;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSystemVisitor implements FileVisitor<Path>{

    @Override
    public FileVisitResult postVisitDirectory(Path arg0, IOException arg1) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path arg0, BasicFileAttributes arg1) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
