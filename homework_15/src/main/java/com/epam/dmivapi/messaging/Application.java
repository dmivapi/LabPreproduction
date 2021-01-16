package com.epam.dmivapi.messaging;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class Application {
	public static final int MSG_NUM_THRESHOLD = 15;
	public static final Random random = new Random(47);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		AdditionService service = ctx.getBean(AdditionService.class);
		return args -> {
			random.ints(MSG_NUM_THRESHOLD, 1, 20)
					.mapToObj(n -> new AddendsMessage(n, n + 3))
					.forEach(service::sendAdditionRequestMessage);
		};
	}
}
