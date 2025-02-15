package com.example.rental_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.rental_management.repository")
@EntityScan(basePackages = "com.example.rental_management.entity")
public class RentalManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(RentalManagementApplication.class, args);
	}
}

