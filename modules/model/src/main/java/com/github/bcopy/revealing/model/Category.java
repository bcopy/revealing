package com.github.bcopy.revealing.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Category {
	@Id
	String id;
	String title;
	Long created;
	Long modified;

	Category parent;

	Map<String, Item> items = new HashMap<>();

	public Item getOrCreateItemByName(String name) {
		return items.computeIfAbsent(name, key -> {
			Item item = new Item();
			item.setTitle(name);
			return item;
		});
	}
}
