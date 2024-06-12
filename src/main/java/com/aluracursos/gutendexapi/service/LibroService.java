package com.aluracursos.gutendexapi.service;
import com.aluracursos.gutendexapi.models.Libro;
import com.aluracursos.gutendexapi.repository.AutorRepository;
import com.aluracursos.gutendexapi.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    LibroRepository libroRepository;

    @Autowired
    AutorRepository autorRepository;


    public Libro comprobarExistenciaLibro(String libroBuscado) {
        System.out.println(libroBuscado);
        Optional<Libro> libro = libroRepository.findByTituloContainsIgnoreCase(libroBuscado);
        System.out.println("Información:" +libro);
        if (libro.isPresent()) {
            System.out.println("Libro ya está presente en la base de datos");
            return libro.get();
        } else {
            return null;
        }
    }

}
