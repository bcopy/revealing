package com.github.bcopy.revealing.model;

import java.util.List;

import lombok.Data;

@Data
public class Slideshow {
   String id;
   String name;
   
   List<Category> categories;
   
}
