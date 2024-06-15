package com.aluracursos.gutendexapi.repository;

import com.aluracursos.gutendexapi.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutor);

    @Query("SELECT a FROM Autor a " +
            "WHERE :inputYear >= CAST(a.fechaDeNacimiento AS integer) " +
            "AND (:inputYear <= COALESCE(CAST(a.fechaDeMuerte AS integer), :inputYear))")
    List<Autor> encontrarAutoresVivosEnAno(@Param("inputYear") int inputYear);

    List<Autor> findByNombre(String nombreAutor);
}
