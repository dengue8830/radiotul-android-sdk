package com.amla.radiotulsdktestcase.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class TipoComentario {
    private int id;
    private String tipo;

    public static final int ID_BUENOS_COMENTARIOS = 1;
    public static final int ID_QUEJAS = 2;
    public static final int ID_OTRO = 3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return tipo;
    }

    public void setNombre(String tipo) {
        this.tipo = tipo;
    }

    public TipoComentario(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static List<TipoComentario> getTipoComentarios() {
        List<TipoComentario> tiposComentarios = new ArrayList<>();
        //Primero esta opcion, porque las otras deben ser elegidas expresamente porque son un mayor impacto en las estadisticas
        tiposComentarios.add(new TipoComentario(ID_OTRO, "Otro"));
        tiposComentarios.add(new TipoComentario(ID_BUENOS_COMENTARIOS, "Buenos Comentarios"));
        tiposComentarios.add(new TipoComentario(ID_QUEJAS, "Quejas"));
        return tiposComentarios;
    }

    public static List<String> getNombresTipoComentarios() {
        List<String> nombres = new ArrayList<>();
        for (TipoComentario tipoComentario : getTipoComentarios()) {
            nombres.add(tipoComentario.getNombre());
        }
        return nombres;
    }
}
