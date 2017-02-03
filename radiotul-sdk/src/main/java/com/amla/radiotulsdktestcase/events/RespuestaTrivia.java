package com.amla.radiotulsdktestcase.events;

public class RespuestaTrivia {

    private Integer idRespuesta;
    private Integer idTrivia;
    private String respuesta;
    private boolean estadoCorrecto;

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Integer getIdTrivia() {
        return idTrivia;
    }

    public void setIdTrivia(Integer idTrivia) {
        this.idTrivia = idTrivia;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isEstadoCorrecto() {
        return estadoCorrecto;
    }

    public void setEstadoCorrecto(boolean estadoCorrecto) {
        this.estadoCorrecto = estadoCorrecto;
    }
}
