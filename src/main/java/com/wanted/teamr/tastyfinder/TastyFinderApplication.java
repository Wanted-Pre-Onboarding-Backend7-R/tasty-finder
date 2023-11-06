package com.wanted.teamr.tastyfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TastyFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TastyFinderApplication.class, args);
	}

}
