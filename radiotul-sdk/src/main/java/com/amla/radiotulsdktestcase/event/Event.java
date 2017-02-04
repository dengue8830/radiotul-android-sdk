package com.amla.radiotulsdktestcase.event;

import com.amla.radiotulsdktestcase.Constants;

import java.util.Date;
import java.util.List;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class Event {

    private Long id;
    private String name;
    private String description;
    private EventType type;
    private List<Prize> prizes;
    private Date endDate;
    private List<Show> shows;
    /** The questions of the events. i can't find a good translate of the multiplechoice questions
     *  wich is related to our model on server :P */
    private Trivia trivia;
    /** The amount of the contestants in the event*/
    private Long contestantsCount;
    /** Flag to know if i am participating */
    private boolean iAmParticipating;
    /** Parsed date when the event was won*/
    private String parsedWonDate;
    /** Amount of seconds remaining to finish the event*/
    private long secondsRemaining;

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

    public int getCantidadPremios() {
        return prizes.size();
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }

    public Long getContestantsCount() {
        return contestantsCount;
    }

    public void setContestantsCount(Long contestantsCount) {
        this.contestantsCount = contestantsCount;
    }

    public String getTiempoRestante() {
        int segundos = (int) getSecondsRemaining() % 60;
        int minutos = (int) Math.floor(getSecondsRemaining() % 3600 / 60);
        int horas = (int) Math.floor(getSecondsRemaining() % 86400 / 3600);
        long dias = (long) Math.floor(getSecondsRemaining() /60 /60 /24);

        return dias+"d "+horas+"h "+minutos+"m "+segundos+"s";
    }

    public long getSecondsRemaining() {
        return secondsRemaining;
    }

    public void setSecondsRemaining(long secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }

    public boolean isiAmParticipating() {
        return iAmParticipating;
    }

    public void setiAmParticipating(boolean iAmParticipating) {
        this.iAmParticipating = iAmParticipating;
    }

    public boolean isTipoMultipleChoice(){
        return type.isTipoMultipleChoice();
    }

    public Trivia getTrivia() {
        return trivia;
    }

    public void setTrivia(Trivia trivia) {
        this.trivia = trivia;
    }

    public boolean isTipoEventual(){
        return getType().getId() == Constants.EVENT_TYPE_EVENTUAL;
    }

    public boolean isTipoJuego(){
        return getType().getId() == Constants.EVENT_TYPE_GAME;
    }

    public String getParsedWonDate() {
        return parsedWonDate;
    }

    public void setParsedWonDate(String parsedWonDate) {
        this.parsedWonDate = parsedWonDate;
    }
}
