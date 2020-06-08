package com.github.bcopy.revealing.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Item {
	@Id
	String id = UUID.randomUUID().toString();
	String title;
	String caption;

	String absolutePath;
	String relativePath;

	Long created;
	Long modified;

	String creator;
	String modificator;

}
