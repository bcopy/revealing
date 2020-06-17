package com.github.bcopy.revealing.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Slideshow {
	@Id
	String id = UUID.randomUUID().toString();
	String name;

	Map<String, Category> categories = new HashMap<>();

	public Category getOrCreateCategoryByName(String name) {
		return categories.computeIfAbsent(name, key -> {
			Category category = new Category();
			category.setTitle(name);
			return category;
		});
	}
}
