package com.github.bcopy.revealing.process;

public interface Processor<T>{
    Cursor process(Cursor cursor, T... arg);
}
