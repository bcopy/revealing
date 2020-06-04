package com.github.bcopy.revealing.model;

import lombok.Data;

@Data
public class Item {
   String id;
   String name;
   
   String absolutePath;
   String relativePath;
   
}
