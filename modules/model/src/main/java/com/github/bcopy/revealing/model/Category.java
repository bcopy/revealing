package com.github.bcopy.revealing.model;

import java.util.ArrayList;
import java.util.List;

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

	List<Item> items = new ArrayList<>();
}
