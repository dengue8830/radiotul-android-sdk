package com.amla.radiotulsdk.event;

import java.util.List;

public class Trivia {

    private Long idTrivia;
    private Long triviaTypeId;
    private String titulo;
    private List<TriviaQuestion> questions;

    public Long getIdTrivia() {
        return idTrivia;
    }

    public void setIdTrivia(Long idTrivia) {
        this.idTrivia = idTrivia;
    }

    public Long getTriviaTypeId() {
        return triviaTypeId;
    }

    public void setTriviaTypeId(Long triviaTypeId) {
        this.triviaTypeId = triviaTypeId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<TriviaQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<TriviaQuestion> questions) {
        this.questions = questions;
    }
}
