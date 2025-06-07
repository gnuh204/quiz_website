package com.example.qiuzweb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.QuizRating;
import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.repository.QuizRatingRepository;

@Service
public class QuizRatingServiceImpl implements QuizRatingService {

    @Autowired
    private QuizRatingRepository ratingRepository;

    @Override
    public Optional<QuizRating> findByUserAndQuiz(User user, Quiz quiz) {
        return ratingRepository.findByUserAndQuiz(user, quiz);
    }

    @Override
    public void saveOrUpdateRating(Quiz quiz, User user, int ratingValue) {
        QuizRating rating = ratingRepository.findByUserAndQuiz(user, quiz).orElse(new QuizRating());

        rating.setQuiz(quiz);
        rating.setUser(user);
        rating.setRating(ratingValue);
        ratingRepository.save(rating);
    }
}
