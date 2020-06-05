package com.github.bcopy.revealing.visitor;


import com.github.bcopy.revealing.model.Category;

public interface Visitor<T> {
    Category process(T arg);
}
