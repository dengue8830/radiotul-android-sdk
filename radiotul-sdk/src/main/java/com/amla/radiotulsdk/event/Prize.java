package com.amla.radiotulsdk.event;

import java.util.List;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class Prize {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    //TODO: que es esto?
    private boolean PrizeStatus;
    private List<String> pictures;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPrizeStatus() {
        return PrizeStatus;
    }

    public void setPrizeStatus(boolean prizeStatus) {
        this.PrizeStatus = prizeStatus;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}
