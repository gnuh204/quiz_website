package com.example.qiuzweb.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.qiuzweb.domain.Question;
import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.service.QuestionService;
import com.example.qiuzweb.service.QuizService;

@Controller
@RequestMapping("/quizzes")
public class PlayController {
    private final QuizService quizService;
    private final QuestionService questionService;
    public PlayController(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }
     @GetMapping("/take/{quizId}/question/{index}")
    public String takeQuiz(@PathVariable("quizId") Long quizId,
                           @PathVariable("index") int index,
                           @RequestParam(name = "score", defaultValue = "0") int score,
                           Model model) {

        Quiz quiz = quizService.getQuizById(quizId);
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);

        if (index >= questions.size()) {
            model.addAttribute("finalScore", score);
            model.addAttribute("totalQuestions", questions.size());
            model.addAttribute("quizTitle", quiz.getTitle());
            return "client/play/quizResultPage";
        }

        Question currentQuestion = questions.get(index);

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("score", score);
        model.addAttribute("currentIndex", index);
        model.addAttribute("totalQuestions", questions.size());

        return "client/play/PlayQuizPage";
    }
    @PostMapping("/take/{quizId}/question/{index}/answer")
    public String submitAnswer(@PathVariable("quizId") Long quizId,
                               @PathVariable("index") int index,
                               @RequestParam("score") int score,
                               @RequestParam("isCorrect") boolean isCorrect) {
        int updatedScore = isCorrect ? score + 1 : score;
        int nextIndex = index + 1;

        return "redirect:/quizzes/take/" + quizId + "/question/" + nextIndex + "?score=" + updatedScore;
    }
}
