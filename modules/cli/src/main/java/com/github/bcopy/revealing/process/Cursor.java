package com.github.bcopy.revealing.process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.bcopy.revealing.model.Category;
import com.github.bcopy.revealing.model.Item;
import com.github.bcopy.revealing.model.Slideshow;

import lombok.Data;

/**
 * Encompasses the current state of a slideshow being assembled by 
 * a visitor implementation.
 */
@Data
public class Cursor {
   Slideshow currentSlideshow;
   Category currentCategory;
   Item currentItem;
   
   AtomicInteger hierarchyLevel;
   
   List<Slideshow> slideshows = new ArrayList<>();
   
}
