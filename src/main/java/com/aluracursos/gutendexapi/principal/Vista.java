package com.aluracursos.gutendexapi.principal;

import java.util.Scanner;

public class Vista {
    private Scanner teclado= new Scanner(System.in);
    public int menuPrincipal(){
        System.out.println("""
                    ***************Bienvenido a tu biblioteca personal***************
                    --Aquí podrás encontrar información de los libros que necesites--
                    Elije la opción de acuerdo al número:
                    1- Buscar libros por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma
                    """);
        return teclado.nextInt();
    }
    public void solicitarNombreLibro(){
        System.out.println("Ingrese el nombre del libro: ");
    }
}
