package ru.improve.openfy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OpenfySharingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenfySharingServiceApplication.class, args);
	}
}
