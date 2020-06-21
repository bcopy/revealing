package com.github.bcopy.revealing.model;

import java.util.Map;
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
	
	Boolean displayed;

	String creator;
	String modificator;
	
	Map<String, String> metadata;
	

}
