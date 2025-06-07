package com.example.qiuzweb.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "user_answers")
public class UserAnswer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne @JoinColumn(name = "choice_id")
    private Choice choice;
    private String correctChoiceText;

    public String getCorrectChoiceText() {
        return correctChoiceText;
    }

    public void setCorrectChoiceText(String correctChoiceText) {
        this.correctChoiceText = correctChoiceText;
    }
    public UserAnswer() {
    }
    public UserAnswer( User user, Quiz quiz, Question question, Choice choice, LocalDateTime answeredAt) {
        this.user = user;
        this.quiz = quiz;
        this.question = question;
        this.choice = choice;
        this.answeredAt = answeredAt;
    }

    private LocalDateTime answeredAt;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(LocalDateTime answeredAt) {
        this.answeredAt = answeredAt;
    }

    
}