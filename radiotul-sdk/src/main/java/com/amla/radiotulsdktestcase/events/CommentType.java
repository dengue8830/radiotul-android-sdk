package com.amla.radiotulsdktestcase.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class CommentType {
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

    public CommentType(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static List<CommentType> getTipoComentarios() {
        List<CommentType> tiposComentarios = new ArrayList<>();
        //Primero esta opcion, porque las otras deben ser elegidas expresamente porque son un mayor impacto en las estadisticas
        tiposComentarios.add(new CommentType(ID_OTRO, "Otro"));
        tiposComentarios.add(new CommentType(ID_BUENOS_COMENTARIOS, "Buenos Comentarios"));
        tiposComentarios.add(new CommentType(ID_QUEJAS, "Quejas"));
        return tiposComentarios;
    }

    public static List<String> getNombresTipoComentarios() {
        List<String> nombres = new ArrayList<>();
        for (CommentType commentType : getTipoComentarios()) {
            nombres.add(commentType.getNombre());
        }
        return nombres;
    }
}
