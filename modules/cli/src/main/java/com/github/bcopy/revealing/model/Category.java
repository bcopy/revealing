package com.github.bcopy.revealing.model;

import lombok.Data;

@Data
public class Category {
   String id;
   String name;
   Long created;
   Long modified;
   
   Category parent;
}
