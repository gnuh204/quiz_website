package com.example.qiuzweb.service;

import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.Choice;
import com.example.qiuzweb.repository.ChoiceRepository;

@Service
public class ChoiceService {
      private final ChoiceRepository choiceRepository;

    public ChoiceService(ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    // Lấy Choice theo ID
    public Choice getChoiceById(Long choiceId) {
        return choiceRepository.findById(choiceId)
                .orElseThrow(() -> new RuntimeException("Choice not found with ID: " + choiceId));
    }
}
