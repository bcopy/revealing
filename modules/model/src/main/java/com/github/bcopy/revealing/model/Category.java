package com.github.bcopy.revealing.model;

import java.util.List;

import lombok.Data;

@Data
public class Category {
   String id;
   String name;
   Long created;
   Long modified;
   
   Category parent;
   
   List<Category> childCategories;
   List<Item> items;
}
