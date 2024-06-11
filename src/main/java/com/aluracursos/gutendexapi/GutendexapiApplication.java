package com.aluracursos.gutendexapi;

import com.aluracursos.gutendexapi.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GutendexapiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GutendexapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal= new Principal();
		principal.mostrarMenu();
	}
}
