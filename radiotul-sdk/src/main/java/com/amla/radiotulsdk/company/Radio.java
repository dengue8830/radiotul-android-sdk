package com.amla.radiotulsdk.company;

/**
 * Created by dengue8830 on 2/3/17.
 */

public class Radio {
    private Long id;
    private String name;
    private String urlStreaming;
    private Long typeId;
    private String typeLabel;
    private String phone;
    private String phoneWithWhatsapp;
    private boolean active;

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

    public String getUrlStreaming() {
        return urlStreaming;
    }

    public void setUrlStreaming(String urlStreaming) {
        this.urlStreaming = urlStreaming;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneWithWhatsapp() {
        return phoneWithWhatsapp;
    }

    public void setPhoneWithWhatsapp(String phoneWithWhatsapp) {
        this.phoneWithWhatsapp = phoneWithWhatsapp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
