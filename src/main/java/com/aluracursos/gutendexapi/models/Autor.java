package com.aluracursos.gutendexapi.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="AUTOR")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeMuerte;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Libro> libros;
    public Autor(){

    }
    public Autor(long id, String nombre, String fechaDeNacimiento, String fechaDeMuerte, List<Libro> libros) {
        this.id = id;
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeMuerte = fechaDeMuerte;
        this.libros = libros;
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre=datosAutor.nombre();
        this.fechaDeNacimiento=datosAutor.fechaDeNacimiento();
        this.fechaDeMuerte=datosAutor.fechaDeMuerte();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(String fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  "-------LIBRO-------"+"\n" +
                "Titulo:" + nombre + "\n" +
                "Fecha Nacimiento:" + fechaDeNacimiento + '\n' +
                "Fecha de muerte:" + fechaDeMuerte + '\n' +
                "-------------------"+"\n";
    }
}
