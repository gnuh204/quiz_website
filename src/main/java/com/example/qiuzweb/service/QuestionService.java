package com.example.qiuzweb.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.qiuzweb.domain.Choice;
import com.example.qiuzweb.domain.Question;
import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.repository.QuestionRepository;
import com.example.qiuzweb.repository.QuizRepository;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizrepository;

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizrepository) {
        this.quizrepository = quizrepository;
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuiz_QuizId(quizId);
    }

    public void saveQuestionWithChoices(Question question, Long quizId, MultipartFile imageFile) {
       
        Quiz quiz = quizrepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz không tồn tại: " + quizId));
        question.setQuiz(quiz);

        
        String imgurl = null;
        try {
            String fileName = imageFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/images/questionImages");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            imgurl = fileName;
            question.setImageUrl(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu ảnh", e);
        }

        // Gắn mỗi Choice vào Question
        if (question.getChoices() != null) {
            for (Choice choice : question.getChoices()) {
                choice.setQuestion(question);
            }
        }

       
        questionRepository.save(question);
    }
    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

}
