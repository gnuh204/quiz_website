package com.example.qiuzweb.service;

import java.security.Principal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.repository.QuizRepository;
import com.example.qiuzweb.repository.UserRepository;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }
    public void saveQuiz(String title,
            String description,
            Long categoryId,
            Integer timeLimitMinutes,
            MultipartFile imageFile,
            Principal principal) {

        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // Category category = categoryRepository.findById(categoryId)
        // .orElseThrow(() -> new RuntimeException("Không tìm thấy chủ đề"));
        String imageUrl = null;
        if (!imageFile.isEmpty()) {
            imageUrl = "/images/quiz" + imageFile.getOriginalFilename(); // có thể upload thật nếu cần
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
        // quiz.setCategory(category);
        quiz.setTimeLimitMinutes(timeLimitMinutes);
        quiz.setImageUrl(imageUrl);
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setCreatedBy(user);
        quizRepository.save(quiz);
    }
}
