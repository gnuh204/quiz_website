package com.example.qiuzweb.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_comments")
public class QuizComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    private String comment;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    public Long getCommentId() {
        return commentId;
    }
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    
   
}