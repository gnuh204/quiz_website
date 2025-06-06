package com.example.qiuzweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qiuzweb.domain.Question;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
   List<Question> findByQuiz_QuizId(Long quizId); 
    
}
