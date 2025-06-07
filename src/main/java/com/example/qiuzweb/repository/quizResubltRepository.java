package com.example.qiuzweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qiuzweb.domain.QuizResult;


@Repository
public interface quizResubltRepository extends JpaRepository<QuizResult, Long> {
    // This repository can be used to perform CRUD operations on QuizResult entities.
    // This interface can be used

}
