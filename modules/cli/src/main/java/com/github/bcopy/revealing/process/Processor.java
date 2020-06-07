package com.github.bcopy.revealing.process;

import com.github.bcopy.revealing.model.Slideshow;

public interface Processor<T> {
    Slideshow process(T arg);
}
