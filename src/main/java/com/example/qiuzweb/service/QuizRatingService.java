package com.example.qiuzweb.service;

import java.util.List;
import java.util.Optional;



import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.QuizRating;
import com.example.qiuzweb.domain.User;

public interface QuizRatingService {
    Optional<QuizRating> findByUserAndQuiz(User user, Quiz quiz);
    void saveOrUpdateRating(Quiz quiz, User user, int rating);
   
}
