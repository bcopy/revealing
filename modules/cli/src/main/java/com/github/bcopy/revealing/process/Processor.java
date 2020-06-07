package com.github.bcopy.revealing.process;


public interface Processor<T> {
    void process(Cursor cursor, T arg);
}
