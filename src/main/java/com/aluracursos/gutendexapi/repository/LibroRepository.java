package com.aluracursos.gutendexapi.repository;

import com.aluracursos.gutendexapi.models.Autor;
import com.aluracursos.gutendexapi.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String libroBuscado);

    List<Libro> findByLenguajes(String idioma);

    @Query("SELECT l FROM Libro l ORDER BY l.numeroDescargas DESC")
    List<Libro> findTop5Libros();


}
