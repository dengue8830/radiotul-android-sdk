package com.amla.radiotulsdktestcase.events;

/**
 * Created by dengue8830 on 28/10/16.
 */
public class Show {
    private Integer id;
    private String name;
    private String description;
    /** 24hs format */
    private String startTime;
    /** 24hs format */
    private String endTime;
    private String facebookUrl;
    private String twitterUrl;
    private String speakerName;
    private String phone;
    private String phoneWithWhatsapp;

    public String getName() {
        return name;
    }

    public void setName(String nombrePrograma) {
        this.name = nombrePrograma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Show() {}

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
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
}
