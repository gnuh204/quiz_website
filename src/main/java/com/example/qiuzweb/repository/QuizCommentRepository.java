package com.example.qiuzweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.QuizComment;
@Repository
public interface QuizCommentRepository extends JpaRepository<QuizComment, Long> {
     List<QuizComment> findByQuizOrderByCreatedAtDesc(Quiz quiz);
}
