package com.aluracursos.gutendexapi.repository;

import com.aluracursos.gutendexapi.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String libroBuscado);
}
