package com.amla.radiotulsdk.events;

import java.util.List;

public class PreguntaTrivia {

    private Integer idPregunta;
    private Integer idTrivia;
    private String pregunta;
    private boolean respondida;
    private List<RespuestaTrivia> respuestasTrivia;

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Integer getIdTrivia() {
        return idTrivia;
    }

    public void setIdTrivia(Integer idTrivia) {
        this.idTrivia = idTrivia;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public List<RespuestaTrivia> getRespuestasTrivia() {
        return respuestasTrivia;
    }

    public void setRespuestasTrivia(List<RespuestaTrivia> respuestasTrivia) {
        this.respuestasTrivia = respuestasTrivia;
    }

    public boolean isRespondida() {
        return respondida;
    }

    public void setRespondida(boolean respondida) {
        this.respondida = respondida;
    }
}
