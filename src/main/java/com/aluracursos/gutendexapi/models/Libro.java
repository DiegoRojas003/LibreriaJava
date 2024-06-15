package com.aluracursos.gutendexapi.models;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="LIBRO")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    String titulo;
    @ManyToOne()
    private Autor autor;
    String nombreAutor;
    String lenguajes;
    Double numeroDescargas;
    public Libro(){

    }
    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.nombreAutor=datosLibros.autor().stream()
                .findFirst()  // Obtiene el primer autor si existe
                .map(DatosAutor::nombre)  // Transforma al nombre del autor
                .orElse("Autor Desconocido");
        this.lenguajes = datosLibros.lenguajes().toString().replace("[","").replace("]","");
        this.numeroDescargas = datosLibros.numeroDescargas();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String lenguajes) {
        this.lenguajes = lenguajes;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return  "-------LIBRO-------"+"\n" +
                "Titulo:" + titulo + "\n" +
                "Autor:" + nombreAutor + '\n' +
                "Idioma:" + lenguajes + '\n' +
                "Numero de descargas:" + numeroDescargas +'\n' +
                "-------------------"+"\n";
    }

}
