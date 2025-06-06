package com.example.qiuzweb.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.QuizComment;
import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.repository.QuizCommentRepository;
import com.example.qiuzweb.repository.QuizRepository;
import com.example.qiuzweb.repository.UserRepository;
@Service
public class QuizCommentService {
    private final QuizCommentRepository quizCommentRepository;
 
    public QuizCommentService(QuizCommentRepository quizCommentRepository, QuizRepository quizRepository, UserRepository userRepository) {
        this.quizCommentRepository = quizCommentRepository;
        
    }
     public List<QuizComment> getCommentsByQuiz(Quiz quiz) {
        return quizCommentRepository.findByQuizOrderByCreatedAtDesc(quiz);
    }
        public QuizComment addComment(Quiz quiz, User user, String commentText) {
        QuizComment comment = new QuizComment();
        comment.setQuiz(quiz);
        comment.setUser(user);
        comment.setComment(commentText);
        return quizCommentRepository.save(comment);
    }
    public void deleteIfOwner(Long commentId, User currentUser) {
    QuizComment c = quizCommentRepository.findById(commentId).orElseThrow();
    if (c.getUser().getUserId().equals(currentUser.getUserId())) {
    quizCommentRepository.delete(c);
    } else {
        throw new AccessDeniedException("Bạn không có quyền xóa bình luận này");
    }
}
public Long findQuizIdByCommentId(Long commentId) {
        return quizCommentRepository.findById(commentId)
            .map(comment -> comment.getQuiz().getQuizId())
            .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    
}
