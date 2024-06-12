package com.aluracursos.gutendexapi.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="LIBRO")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    String titulo;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Libro_Autor",
            joinColumns = @JoinColumn(name="libro_id"),
            inverseJoinColumns=@JoinColumn(name="autor_id")
    )
    List<Autor> autores;
    List<String> lenguajes;
    Double numeroDescargas;
    public Libro(){

    }
    public Libro(String titulo, List<Autor> autores, List<String> lenguajes, Double numeroDescargas) {
        this.titulo = titulo;
        this.autores = autores;
        this.lenguajes = lenguajes;
        this.numeroDescargas = numeroDescargas;
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

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = lenguajes;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}
