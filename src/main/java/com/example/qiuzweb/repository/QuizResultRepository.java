package com.example.qiuzweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qiuzweb.domain.QuizResult;
@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
     List<QuizResult> findByUser_UserId(Long userId);
    List<QuizResult> findByQuiz_QuizId(Long quizId);
    
}
