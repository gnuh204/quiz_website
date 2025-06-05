package com.example.qiuzweb.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
        this.categoryRepository = categoryRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    private String getEmailFromAuthentication(Authentication authentication) {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // với Local, username thường là email
        } else if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User oAuth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            return oAuth2User.getAttribute("email");
        } else {
            throw new RuntimeException("Không xác định được loại đăng nhập");
        }
    }

    public void saveQuiz(String title,
            String description,
            Long categoryId,
            Integer timeLimitMinutes,
            MultipartFile imageFile,
            Authentication authentication) {

        // ✅ Lấy email từ authentication bất kể là Local hay OAuth2
        String email = getEmailFromAuthentication(authentication);

        // ✅ Tìm user trong database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        // ✅ Tìm category
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chủ đề"));

        // ✅ Lưu ảnh
        String imgurl = null;
        try {
            String fileName = imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/images/quizImages");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            imgurl = fileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu ảnh", e);
        }

        // ✅ Tạo quiz
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

    public List<Quiz> getQuizzesByCurrentUser(User currentUser) {
        return quizRepository.findByCreatedBy(currentUser);
    }

    public Quiz getQuizByIdAndUser(Long quizId, User currentUser) {
        return quizRepository.findByQuizIdAndCreatedBy(quizId, currentUser)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }
}
