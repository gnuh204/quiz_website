package com.example.qiuzweb.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.User;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {  
      List<Quiz> findByCreatedBy(User user);
    
    // Tìm quiz theo ID và người tạo (để kiểm tra quyền sở hữu)
    Optional<Quiz> findByQuizIdAndCreatedBy(Long quizId, User user);
} 
