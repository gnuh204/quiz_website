package com.example.qiuzweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.QuizRating;
import com.example.qiuzweb.domain.User;

@Repository
public interface QuizRatingRepository extends JpaRepository<QuizRating, Long> {
    Optional<QuizRating> findByUserAndQuiz(User user, Quiz quiz);
}
