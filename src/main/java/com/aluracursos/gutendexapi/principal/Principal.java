package com.aluracursos.gutendexapi.principal;

import com.aluracursos.gutendexapi.models.Datos;
import com.aluracursos.gutendexapi.models.DatosLibros;
import com.aluracursos.gutendexapi.service.ConsumoAPI;
import com.aluracursos.gutendexapi.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor= new ConvierteDatos();
    private Scanner teclado= new Scanner(System.in);

    private static final String URL_BASE="http://gutendex.com/books/";
    private static final String URL_BUSQUEDA="http://gutendex.com/books/?search=";
    public void mostrarMenu(){
        
    }

    public void consumirApi(){
        String json= consumoAPI.obtenerDatos(URL_BASE);
        var datos=conversor.obtenerDatos(json, Datos.class);
        System.out.println(json);
        System.out.println("Datos:");
        List<DatosLibros> ListaLibros=datos.libros().stream().collect(Collectors.toList());
        ListaLibros.stream().limit(5).forEach(System.out::println);
    }
    public void top5(){
        System.out.println("Top 10 libros más descargados:");
        List<String>TopLibros= ListaLibros.stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                .limit(10).map(DatosLibros::titulo).collect(Collectors.toList());
        TopLibros.stream().forEach(System.out::println);
    }

    public void busquedaLibro(){
        System.out.println("Ingrese la pelicula que desea buscar:");
        var libroConsultado=teclado.nextLine();
        //Busqueda en base a los datos del json
        Optional<DatosLibros> libroBuscado=ListaLibros.stream()
                .filter(e-> e.titulo().toUpperCase()
                        .equals(libroConsultado.toUpperCase())).findFirst();
        //Busqueda por medio de la API
        var libroBuscadoApi=consumoAPI.obtenerDatos(URL_BUSQUEDA+libroConsultado);
        var datosLista=conversor.obtenerDatos(libroBuscadoApi,Datos.class);

        if(libroBuscado.isPresent() ){
            System.out.println("Libro Encontrado");
            System.out.println("El libro es: "+libroBuscado.get());
        }else if(datosLista.contador()>0) {
            System.out.println("Libro Encontrado, total de coincidencias: "+datosLista.contador());
            System.out.println(datosLista);
        }else {
            System.out.println("Libro no encontrado");
        }
    }
    public void estadisticas(){
        DoubleSummaryStatistics est= ListaLibros.stream().collect(Collectors.summarizingDouble(DatosLibros::numeroDescargas));
        System.out.println("Media de las descargas: "+est.getAverage());
        System.out.println("Numero mayor de descargas: "+est.getMax());
        System.out.println("Numero menor de descargas: "+est.getMin());
        System.out.println("Total de descargas: "+est.getSum());
        System.out.println("Total registros valorados: "+est.getCount());
    }
}
