package com.aluracursos.literalura;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.RespuestaAPI;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class Principal {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired AutorRepository autorRepository;
    private Scanner teclado = new Scanner(System.in);
    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConvierteDatos conversor;

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private List<DatosLibro> datosLibros = new ArrayList<>();

    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Lista libros por idiomas
                    0- Salir
                    """;

            System.out.println(menu);
            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine(); // limpiar entrada incorrecta
            } else {
                System.out.println("Entrada inválida, por favor ingrese un número");
                teclado.nextLine(); // limpiar entrada incorrecta
                opcion = -1; // vuelve a mostrar el menú
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarautores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación");
                    break;
                default:
                    System.out.println("Por favor ingrese una opción válida");
            }


        }

    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma que desea consultar (en = Inglés, es = Español)");
        String idioma = teclado.nextLine();

        List<Libro> libros = libroRepository.findByIdiomasContaining(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año que desea consultar");
        int anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivos = autorRepository
                .findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqualOrFallecimientoIsNull(anio, anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos ese año.");
        } else {
            System.out.println("Los autores vivos en el año " + anio + " son:");
            autoresVivos.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println);
        }
    }

    private void listarautores() {
        List<Autor> autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAllWithAutores();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    DatosLibro buscarLibroPorTitulo() {
        System.out.println("Ingrese el titulo del libro que desea buscar");
        var tituloDelLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + URLEncoder.encode(tituloDelLibro, StandardCharsets.UTF_8));

        RespuestaAPI respuesta = conversor.obtenerDatos(json, RespuestaAPI.class);

        if (respuesta.results() == null || respuesta.results().isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
            return null;
        }

        DatosLibro datos = respuesta.results().get(0);

        // Verifica si ya existe
        if (libroRepository.findByTitulo(datos.titulo()).isPresent()) {
            System.out.println("El libro ya está registrado en la base de datos.");
            return datos;
        }

        // Guarda el libro con autores
        Libro libro = guardarLibroDesdeDatos(datos);

        System.out.println("Libro guardado correctamente:");
        System.out.println(libro);

        return datos;
    }

    private Libro guardarLibroDesdeDatos(DatosLibro datos) {
        List<Autor> autoresPersistidos = datos.autores().stream()
                .map(a -> autorRepository.findByNombre(a.name())
                        .orElseGet(() -> autorRepository.save(new Autor(a.name(), a.nacimiento(), a.fallecimiento()))))
                .toList();

        Libro libro = new Libro();
        libro.setTitulo(datos.titulo());
        libro.setIdiomas(datos.idioma());
        libro.setGeneros(datos.genero());
        libro.setDescargas(datos.descargas());
        libro.setAutores(autoresPersistidos);

        return libroRepository.save(libro);
    }
}