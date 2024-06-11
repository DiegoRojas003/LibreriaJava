package com.aluracursos.gutendexapi.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors")List<DatosAutor> autor,
        @JsonAlias("languajes")List<String> lenguajes,
        @JsonAlias("download_count")Double numeroDescargas
) {
    @Override
    public String toString() {
        return
                "Titulo:" + titulo + '\'' +
                ", Autor:" + autor +
                ", Lenguajes:" + lenguajes +
                ",y el numero de descargas: " + numeroDescargas;
    }
}
