package com.amla.radiotulsdktestcase.event;

import com.amla.radiotulsdktestcase.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class CommentType {
    private int id;
    private String tipo;

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
        tiposComentarios.add(new CommentType(Constants.COMMENT_TYPE_GOOD, "Buenos Comentarios"));
        tiposComentarios.add(new CommentType(Constants.COMMENT_TYPE_BAD, "Quejas"));
        tiposComentarios.add(new CommentType(Constants.COMMENT_TYPE_OTHER, "Otro"));
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
