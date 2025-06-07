package com.example.qiuzweb.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_results")
public class QuizResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    private Float score;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private LocalDateTime takenAt;
    public Long getResultId() {
        return resultId;
    }
    public void setResultId(Long resultId) {
        this.resultId = resultId;
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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }

    
}