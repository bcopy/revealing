package com.github.bcopy.revealing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class Slideshow {
	String id = UUID.randomUUID().toString();
	String name;

	List<Category> categories = new ArrayList<>();
}
