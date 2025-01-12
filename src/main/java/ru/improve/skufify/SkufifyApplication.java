package ru.improve.skufify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SkufifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkufifyApplication.class, args);
	}
}
