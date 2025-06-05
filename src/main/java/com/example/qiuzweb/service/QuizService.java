package com.example.qiuzweb.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.qiuzweb.domain.Category;
import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.repository.CategoryRepository;
import com.example.qiuzweb.repository.QuizRepository;
import com.example.qiuzweb.repository.UserRepository;

import java.io.IOException;
import java.nio.file.*;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository,
                       CategoryRepository categoryRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // ✅ Hàm lấy tất cả bài quiz
 public List<Quiz> getAllQuizzes() {
    return quizRepository.findAllWithCreatedBy();
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

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chủ đề"));
        String imgurl = null;
        try {
            String fileName = imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/images/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            imgurl = fileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu ảnh", e);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setCategory(category);
        quiz.setTimeLimitMinutes(timeLimitMinutes);
        quiz.setImageUrl(imgurl);
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setCreatedBy(user);
        quizRepository.save(quiz);
    }
}
