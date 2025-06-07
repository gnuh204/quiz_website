package com.example.qiuzweb.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.qiuzweb.domain.UserAnswer;

import jakarta.transaction.Transactional;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
@Transactional
@Modifying
@Query("DELETE FROM UserAnswer ua WHERE ua.user.userId = :userId AND ua.question.quiz.quizId = :quizId")
void deleteByUserIdAndQuizId(@Param("userId") Long userId, @Param("quizId") Long quizId);
     List<UserAnswer> findByUserUserIdAndQuizQuizId(Long userId, Long quizId);
}

