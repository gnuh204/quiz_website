package com.example.qiuzweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.qiuzweb.domain.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {  
} 
