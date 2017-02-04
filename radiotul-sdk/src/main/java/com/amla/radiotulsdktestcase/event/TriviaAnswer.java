package com.amla.radiotulsdktestcase.event;

public class TriviaAnswer {

    private Long id;
    private Long triviaId;
    private String answerText;
    private boolean isCorrect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTriviaId() {
        return triviaId;
    }

    public void setTriviaId(Long triviaId) {
        this.triviaId = triviaId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }
}
