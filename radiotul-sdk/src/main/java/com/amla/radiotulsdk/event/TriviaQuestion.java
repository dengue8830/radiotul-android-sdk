package com.amla.radiotulsdk.event;

import java.util.List;

public class TriviaQuestion {

    private Long id;
    private Long triviaId;
    private String questionText;
    private boolean answered;
    private List<TriviaAnswer> answers;

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

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<TriviaAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<TriviaAnswer> answers) {
        this.answers = answers;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
