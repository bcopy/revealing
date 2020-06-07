package com.github.bcopy.revealing.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Category {
   String id;
   String name;
   Long created;
   Long modified;
   
   Category parent;
   
   List<Item> items = new ArrayList<>();
}
