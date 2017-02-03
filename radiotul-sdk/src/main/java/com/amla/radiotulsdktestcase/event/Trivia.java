package com.amla.radiotulsdktestcase.event;

import java.util.List;

public class Trivia {

    private Integer idTrivia;
    private Integer idTipoTrivia;
    private String titulo;
    private List<PreguntaTrivia> preguntasTrivia;

    public Integer getIdTrivia() {
        return idTrivia;
    }

    public void setIdTrivia(Integer idTrivia) {
        this.idTrivia = idTrivia;
    }

    public Integer getIdTipoTrivia() {
        return idTipoTrivia;
    }

    public void setIdTipoTrivia(Integer idTipoTrivia) {
        this.idTipoTrivia = idTipoTrivia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<PreguntaTrivia> getPreguntasTrivia() {
        return preguntasTrivia;
    }

    public void setPreguntasTrivia(List<PreguntaTrivia> preguntasTrivia) {
        this.preguntasTrivia = preguntasTrivia;
    }
}
