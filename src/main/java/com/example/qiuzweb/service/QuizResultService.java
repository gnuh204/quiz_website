package com.example.qiuzweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.QuizResult;
import com.example.qiuzweb.repository.QuizResultRepository;
@Service
public class QuizResultService {
    private final QuizResultRepository quizResultRepository;
    public QuizResultService(QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
    }
    public void saveQuizResult(QuizResult result) {
        quizResultRepository.save(result);
    }

    public List<QuizResult> getResultsByUserId(Long userId) {
        return quizResultRepository.findByUser_UserId(userId);
    }

    public List<QuizResult> getResultsByQuizId(Long quizId) {
        return quizResultRepository.findByQuiz_QuizId(quizId);
    }
    public List<QuizResult> getQuizResultsByUserId(Long userId) {
    return quizResultRepository.findByUser_UserId(userId);
}
}
