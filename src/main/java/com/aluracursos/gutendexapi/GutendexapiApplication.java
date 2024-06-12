package com.aluracursos.gutendexapi;

import com.aluracursos.gutendexapi.principal.Principal;
import com.aluracursos.gutendexapi.repository.AutorRepository;
import com.aluracursos.gutendexapi.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GutendexapiApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(GutendexapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var principal= new Principal(libroRepository,autorRepository);
		principal.mostrarMenu();
	}
}
