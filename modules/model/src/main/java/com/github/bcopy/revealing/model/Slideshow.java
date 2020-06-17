package com.github.bcopy.revealing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Slideshow {
	@Id
	String id = UUID.randomUUID().toString();
	String name;

	Map<String, Category> categoriesMap = new HashMap<>();
	
	List<Category> categories = new ArrayList<>();

	public Category getOrCreateCategoryByName(String name) {
		return categoriesMap.computeIfAbsent(name, key -> {
			Category category = new Category();
			category.setTitle(name);
			categories.add(category);
			return category;
		});
	}
}
