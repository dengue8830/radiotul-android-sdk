package com.amla.radiotulsdktestcase.company;

import java.util.List;

/**
 * Created by dengue8830 on 2/3/17.
 */

public class Company {
    private long id;
    private String name;
    private String adress;
    private String landlinePhone;
    private String mobilePhone;
    private Boolean locked;
    private List<Radio> radios;
    private List<Feature> features;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLandlinePhone() {
        return landlinePhone;
    }

    public void setLandlinePhone(String landlinePhone) {
        this.landlinePhone = landlinePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public List<Radio> getRadios() {
        return radios;
    }

    public void setRadios(List<Radio> radios) {
        this.radios = radios;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
