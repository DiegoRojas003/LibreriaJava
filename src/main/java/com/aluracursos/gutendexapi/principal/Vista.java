package com.aluracursos.gutendexapi.principal;

import java.util.Scanner;

public class Vista {
    private static Scanner teclado= new Scanner(System.in);
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
                    6- Estadisticas de los libros presentes segun numero de descargas
                    7- Top 5 libros más descargados
                    8- Buscar autor por nombre
                    """);
        return teclado.nextInt();
    }
    public void solicitarNombreLibro(){
        System.out.println("Ingrese el nombre del libro: ");
    }
    public void solicitarLenguaje(){
        System.out.println("""
                Digite el idioma por el cual desea listar los libros
                Hagálo segun las siguientes indicaciones:
                en: Ingles
                es: Español
                """);
    }
}
