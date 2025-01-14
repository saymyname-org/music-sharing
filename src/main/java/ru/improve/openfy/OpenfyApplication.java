package ru.improve.openfy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import ru.improve.openfy.core.grpc.GrpcClientService;

@SpringBootApplication
@ConfigurationPropertiesScan
public class OpenfyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(OpenfyApplication.class, args);

		GrpcClientService service = context.getBean(GrpcClientService.class);

		String message = service.sayHello();
		System.out.println(message);
	}
}
