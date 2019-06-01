package com.example.personalLib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PersonalLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalLibApplication.class, args);
	}

}
