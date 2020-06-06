package com.github.bcopy.revealing.visitor;


public interface Visitor<T> {
    void process(Cursor cursor, T arg);
}
