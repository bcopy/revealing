package com.github.bcopy.revealing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@EnableMapRepositories("com.github.bcopy.revealing.repo")
public class RevealingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevealingApplication.class, args);
	}

}
