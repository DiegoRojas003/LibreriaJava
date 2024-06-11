package com.aluracursos.gutendexapi.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("count") Integer contador,
        @JsonAlias("results") List<DatosLibros> libros
) {
    @Override
    public String toString() {
        return "libros=" + libros;
    }
}
