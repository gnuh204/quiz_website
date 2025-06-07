package com.example.qiuzweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.qiuzweb.domain.Choice;
@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    // Custom query methods can be defined here if needed

    
} 
