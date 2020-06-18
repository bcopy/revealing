package com.github.bcopy.revealing.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	Boolean displayed;

	Category parent;

	Map<String, Item> itemsMap = new HashMap<>();
	
	List<Item> items = new ArrayList<>();
	

	public Item getOrCreateItemByName(String name) {
		return itemsMap.computeIfAbsent(name, key -> {
			Item item = new Item();
			item.setTitle(name);
			items.add(item);
			return item;
		});
	}
}
