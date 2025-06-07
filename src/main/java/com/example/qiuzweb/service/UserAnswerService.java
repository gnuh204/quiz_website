package com.example.qiuzweb.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.Choice;
import com.example.qiuzweb.domain.Question;
import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.domain.UserAnswer;
import com.example.qiuzweb.repository.UserAnswerRepository;

@Service
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;
    
    public UserAnswerService(UserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

 public void saveUserAnswer(User user, Quiz quiz, Question question, Choice choice) {
        UserAnswer userAnswer = new UserAnswer(user, quiz, question, choice, LocalDateTime.now());
        userAnswerRepository.save(userAnswer);
    }

    public List<UserAnswer> getUserAnswersByUserIdAndQuizId(Long userId, Long quizId) {
        return userAnswerRepository.findByUserUserIdAndQuizQuizId(userId, quizId);
    }
    public void deleteByUserIdAndQuizId(Long userId, Long quizId) {
    userAnswerRepository.deleteByUserIdAndQuizId(userId, quizId);
}



}
