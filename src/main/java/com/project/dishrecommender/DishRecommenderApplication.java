package com.project.dishrecommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DishRecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishRecommenderApplication.class, args);
	}

}
