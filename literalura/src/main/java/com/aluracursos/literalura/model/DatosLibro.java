package com.aluracursos.literalura.model;

import com.aluracursos.literalura.dto.AutorRecord;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorRecord> autores,
        @JsonAlias("bookshelves") List<String> genero,
        @JsonAlias("download_count") int descargas,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("id") int id) {
    @Override
    public String toString(){
        return "ID: " + id + ",Titulo: "+ titulo + ", Autor(es): " + autores + ", Genero: " + genero + "Idioma: " + idioma;
    }
}
