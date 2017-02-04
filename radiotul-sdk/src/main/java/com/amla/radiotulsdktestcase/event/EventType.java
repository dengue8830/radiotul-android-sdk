package com.amla.radiotulsdktestcase.event;

/**
 * Created by dengue8830 on 28/10/16.
 */
public class EventType {
    public static final Integer MULTIPLE_CHOICE_TYPE_ID = 2;

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTipoMultipleChoice(){
        return MULTIPLE_CHOICE_TYPE_ID.equals(id);
    }
}
