package com.aluracursos.gutendexapi.principal;

import com.aluracursos.gutendexapi.models.Datos;
import com.aluracursos.gutendexapi.models.DatosLibros;
import com.aluracursos.gutendexapi.models.Libro;
import com.aluracursos.gutendexapi.repository.AutorRepository;
import com.aluracursos.gutendexapi.repository.LibroRepository;
import com.aluracursos.gutendexapi.service.ConsumoAPI;
import com.aluracursos.gutendexapi.service.ConvierteDatos;
import com.aluracursos.gutendexapi.service.LibroService;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    //Clases necesarias
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor= new ConvierteDatos();
    private Vista vista= new Vista();
    private LibroService libroService= new LibroService();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private Scanner teclado= new Scanner(System.in);
    //URL DE LA API GUTENDEX
    private static final String URL_BASE="http://gutendex.com/books/";
    private static final String URL_BUSQUEDA="http://gutendex.com/books/?search=";

    //VARIABLES Y LISTAS DE OBJETOS
    List<DatosLibros> ListaLibros;

    public Principal(LibroRepository libroRepository,AutorRepository autorRepository) {
        this.libroRepository=libroRepository;
        this.autorRepository=autorRepository;
    }
    public void mostrarMenu(){
        int opcion=10;
        while (opcion != 0){
            opcion=vista.menuPrincipal();
            switch (opcion){
                case 1:
                    consumirApi();
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Error, ingrese una opción valida");
                    break;
            }

        }
    }

    public void consumirApi(){
        vista.solicitarNombreLibro();
        var nombreLibro=teclado.nextLine();
        var libroEncontrado=libroService.comprobarExistenciaLibro(nombreLibro);
        if(libroEncontrado==null){
            String json= consumoAPI.obtenerDatos(URL_BASE);
            var datos=conversor.obtenerDatos(json, Datos.class);
            System.out.println(json);
            System.out.println("Datos:");
            ListaLibros=datos.libros().stream().collect(Collectors.toList());
            System.out.println(ListaLibros);
        }else{
            System.out.println(libroEncontrado);
        }
    }
    public void top5(){
        System.out.println("Top 10 libros más descargados:");
        List<String>TopLibros= ListaLibros.stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDescargas).reversed())
                .limit(10).map(DatosLibros::titulo).collect(Collectors.toList());
        TopLibros.stream().forEach(System.out::println);
    }

    public void busquedaLibro(){

        //Busqueda en base a los datos del json
        String libroConsultado="";
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
