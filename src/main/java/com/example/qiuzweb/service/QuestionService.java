package com.example.qiuzweb.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public void saveQuestionWithChoices(Question question, Long quizId) {
        // Gắn quiz cho câu hỏi
        Quiz quiz = quizrepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz không tồn tại: " + quizId));
        question.setQuiz(quiz);

        // Gắn mỗi Choice vào Question
        if (question.getChoices() != null) {
            for (Choice choice : question.getChoices()) {
                choice.setQuestion(question);
            }
        }

        // Lưu cả question và cascade các choices
        questionRepository.save(question);
    }
    
}
