package com.github.bcopy.revealing.process;

import com.github.bcopy.revealing.model.Cursor;

public interface Processor<T>{
    @SuppressWarnings("unchecked")
	Cursor process(Cursor cursor, T... arg);
}
