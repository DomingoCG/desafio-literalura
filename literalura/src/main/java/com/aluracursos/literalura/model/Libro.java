package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> generos;

    private Integer descargas;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)

    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    public Libro() {
    }

    // Constructor que recibe un DatosLibro
    public Libro(DatosLibro d) {
        this.titulo = d.titulo();
        this.idiomas = d.idioma() != null ? d.idioma() : new ArrayList<>();
        this.generos = d.genero() != null ? d.genero() : new ArrayList<>();
        this.descargas = d.descargas();
        this.autores = d.autores() != null ?
                d.autores().stream()
                        .map(a -> new Autor(a.name(), a.nacimiento(), a.fallecimiento()))
                        .collect(Collectors.toList()) :
                new ArrayList<>();
    }


    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\nAutores: " + autores +
                "\nIdiomas: " + idiomas + "\nGéneros: " + generos +
                "\nDescargas: " + descargas + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return Objects.equals(titulo, libro.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo);
    }
}
