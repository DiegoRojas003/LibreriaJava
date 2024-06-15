package com.aluracursos.gutendexapi.principal;

import com.aluracursos.gutendexapi.models.Autor;
import com.aluracursos.gutendexapi.models.Datos;
import com.aluracursos.gutendexapi.models.DatosLibros;
import com.aluracursos.gutendexapi.models.Libro;
import com.aluracursos.gutendexapi.repository.AutorRepository;
import com.aluracursos.gutendexapi.repository.LibroRepository;
import com.aluracursos.gutendexapi.service.ConsumoAPI;
import com.aluracursos.gutendexapi.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    //Clases necesarias
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor= new ConvierteDatos();
    private Vista vista= new Vista();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private Scanner teclado= new Scanner(System.in);
    //URL DE LA API GUTENDEX
    private static final String URL_BASE="http://gutendex.com/books/";
    private static final String URL_BUSQUEDA="http://gutendex.com/books/?search=";

    //VARIABLES Y LISTAS DE OBJETOS
    List<DatosLibros> ListaDatosLibros = new ArrayList<>();
    List<Libro> listaLibros = new ArrayList<>();
    List<Autor> listaAutores = new ArrayList<>();
    public Principal(LibroRepository libroRepository,AutorRepository autorRepository) {
        this.libroRepository=libroRepository;
        this.autorRepository=autorRepository;
    }
    public void mostrarMenu(){
        int opcion=10;
        while (opcion != 0){
            try{
                opcion=vista.menuPrincipal();
                switch (opcion){
                    case 1:
                        consumirApi();
                        break;
                    case 2:
                        mostrarTodosLosLibros();
                        break;
                    case 3:
                        mostrarTodosLosAutores();
                        break;
                    case 4:
                        mostrarAutoresSegunAnio();
                        break;
                    case 5:
                        mostrarLibrosSegunIdioma();
                        break;
                    case 6:
                        estadisticas();
                        break;
                    case 7:
                        top5();
                        break;
                    case 8:
                        buscarAutor();
                        break;
                    default:
                        System.out.println("Error, ingrese una opción valida");
                        break;
                }
            }catch (Exception e){
                System.out.println("Digite un valor valido");
            }
        }
    }




    public void consumirApi(){
        vista.solicitarNombreLibro();
        String libro=teclado.nextLine();
        libro=libro.replace(" ","+");
        System.out.println(libro);
        Optional<Libro> libroConsultado =libroRepository.findByTituloContainsIgnoreCase(libro);

        if(libroConsultado.isEmpty()){
            var libroBuscadoApi=consumoAPI.obtenerDatos(URL_BUSQUEDA+libro);
            try{
                var datos=conversor.obtenerDatos(libroBuscadoApi,Datos.class);
                manejoDeDatos(datos);
            }catch (IndexOutOfBoundsException e){
                System.out.println("El libro no fue encontrado, verifique el nombre");
            }
        }else{
            System.out.println("El libro ya se encuentra almacenado");
            System.out.println(libroConsultado.get());
        }
    }
    public void manejoDeDatos(Datos datos){
        System.out.println(datos);
        DatosLibros datosL=datos.libros().get(0);
        System.out.println("DatosLibro");
        System.out.println(datosL);
        Libro libro=new Libro(datosL);
        Autor autor=new Autor(datosL.autor().get(0));
        validarAutor(libro,autor);
    }

    public void validarAutor(Libro libro, Autor autor) {
        String nombreAutor = autor.getNombre();

        // Verificar si el autor ya existe
        Optional<Autor> autorBuscado = autorRepository.findByNombreContainsIgnoreCase(nombreAutor);

        if (autorBuscado.isPresent()) {
            // El autor ya existe, así que lo usamos
            libro.setAutor(autorBuscado.get());
        } else {
            // El autor no existe, guardamos primero el nuevo autor
            Autor autorGuardado = autorRepository.save(autor);
            libro.setAutor(autorGuardado);
        }

        // Ahora guardamos el libro con el autor asociado (existente o nuevo)
        libroRepository.save(libro);
    }

    private void mostrarTodosLosLibros() {
        listaLibros=libroRepository.findAll();
        listaLibros.forEach(System.out::println);
        detenerHilo5Segundos();
    }
    private void mostrarTodosLosAutores(){
        listaAutores=autorRepository.findAll();
        listaAutores.forEach(System.out::println);
        detenerHilo5Segundos();
    }
    private void mostrarAutoresSegunAnio(){
        System.out.println("Ingrese el año:");
        int anio=teclado.nextInt();
        List<Autor> listaAutoresVivos= autorRepository.encontrarAutoresVivosEnAno(anio);
        if (listaAutoresVivos!=null){
            listaAutoresVivos.forEach(System.out::println);
        }else {
            System.out.println("No hay autores vivos en este determinado año");
        }
        detenerHilo5Segundos();
    }
    private void mostrarLibrosSegunIdioma() {
        vista.solicitarLenguaje();
        String idioma=teclado.nextLine();
        listaLibros=libroRepository.findByLenguajes(idioma);
        if(listaLibros!=null){
            listaLibros.forEach(System.out::println);
        }else {
            System.out.println("No se encontro ningun libro con el idioma seleccionado");
        }
        detenerHilo5Segundos();
    }
    private void detenerHilo5Segundos(){
        try {
            Thread.sleep(4000);
        }catch (Exception e){
            System.out.println("No se puede esperar");
        }
    }
    public void top5(){
        System.out.println("Top 5 libros más descargados:");
        listaLibros=libroRepository.findTop5Libros();
        listaLibros.forEach(System.out::println);
    }

    private void buscarAutor() {
        System.out.println("Digite el nombre del autor");
        String nombreAutor=teclado.nextLine();
        listaAutores=autorRepository.findByNombre(nombreAutor);
        if(listaAutores!=null){
            listaAutores.forEach(System.out::println);
        }else {
            System.out.println("El autor no fue encontrado en nuestra base de datos");
        }
        detenerHilo5Segundos();
    }
    public void estadisticas(){
        listaLibros=libroRepository.findAll();
        DoubleSummaryStatistics est= listaLibros.stream().collect(Collectors.summarizingDouble(Libro::getNumeroDescargas));
        System.out.println("Media de las descargas: "+est.getAverage());
        System.out.println("Numero mayor de descargas: "+est.getMax());
        System.out.println("Numero menor de descargas: "+est.getMin());
        System.out.println("Total de descargas: "+est.getSum());
        System.out.println("Total registros valorados: "+est.getCount());
        detenerHilo5Segundos();
    }
}
