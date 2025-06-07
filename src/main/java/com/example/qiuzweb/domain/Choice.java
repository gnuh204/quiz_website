package com.example.qiuzweb.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "choices")
public class Choice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choiceId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String choiceText;
    private Boolean isCorrect;
    public boolean isCorrect() {
    return Boolean.TRUE.equals(isCorrect);
}

    public Long getChoiceId() {
        return choiceId;
    }
    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public String getChoiceText() {
        return choiceText;
    }
    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }
    public Boolean getIsCorrect() {
        return isCorrect;
    }
    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
    

}

