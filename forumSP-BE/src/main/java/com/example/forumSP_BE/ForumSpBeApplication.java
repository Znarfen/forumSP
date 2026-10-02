package com.example.forumSP_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForumSpBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumSpBeApplication.class, args);

		System.out.println("\nServer is running on http://localhost:8080");
	}

}
