package com.example.A2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class A2Application {

	public static void main(String[] args) {
		SpringApplication.run(A2Application.class, args);
	}

}
