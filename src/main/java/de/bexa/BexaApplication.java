package de.bexa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class BexaApplication {
	public static void main(String[] args) {
		SpringApplication.run(BexaApplication.class, args);
	}
}