package com.frew.crew;

import com.frew.crew.user.UserRepository;
import com.frew.crew.user.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrewApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrewApplication.class, args);
	}

}
