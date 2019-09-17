package it.cambi.celum;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import it.cambi.celum.mongo.model.User;
import it.cambi.celum.service.UserService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner initialize(UserService userService) {
		return args -> {

			User fakeUser = new User.Builder().withAddress("Via Stelvio 13, Pistoia").withEmail("lucacambi77@gmail.com")
					.withName("Luca").withLastName("Cambi").build();
			userService.save(fakeUser);

		};
	}
}
