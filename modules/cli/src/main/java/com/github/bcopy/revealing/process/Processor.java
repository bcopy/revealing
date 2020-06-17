package com.github.bcopy.revealing.process;

import com.github.bcopy.revealing.model.Cursor;

public interface Processor<T>{
    Cursor process(Cursor cursor, T... arg);
}
