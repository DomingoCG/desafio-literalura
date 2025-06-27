package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaAPI {
    @JsonProperty("results")
    private List<DatosLibro> results;

    public List<DatosLibro> results() {
        return results;
    }

    public void setResults(List<DatosLibro> results) {
        this.results = results;
    }
}
