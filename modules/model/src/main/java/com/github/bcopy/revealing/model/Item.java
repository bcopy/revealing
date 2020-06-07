package com.github.bcopy.revealing.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Item {
	String id = UUID.randomUUID().toString();
	String name;

	String absolutePath;
	String relativePath;

	Long created;
	Long modified;

	String creator;
	String modificator;

}
