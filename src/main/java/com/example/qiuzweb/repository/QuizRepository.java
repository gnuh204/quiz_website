package com.example.qiuzweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.qiuzweb.domain.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {  
    @EntityGraph(attributePaths = "createdBy")
@Query("SELECT q FROM Quiz q")
List<Quiz> findAllWithCreatedBy();

} 
