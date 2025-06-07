package com.example.qiuzweb.controller.client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.qiuzweb.domain.Choice;
import com.example.qiuzweb.domain.Question;
import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.QuizResult;
import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.domain.UserAnswer;
import com.example.qiuzweb.service.ChoiceService;
import com.example.qiuzweb.service.QuestionService;
import com.example.qiuzweb.service.QuizResultService;
import com.example.qiuzweb.service.QuizService;
import com.example.qiuzweb.service.UserAnswerService;

@Controller
@RequestMapping("/quizzes")
public class PlayController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final ChoiceService choiceService;
    private final UserAnswerService userAnswerService;
    private final QuizResultService quizResultService;

    public PlayController(QuizService quizService, QuestionService questionService,
            ChoiceService choiceService, UserAnswerService userAnswerService,
            QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
        this.quizService = quizService;
        this.questionService = questionService;
        this.choiceService = choiceService;
        this.userAnswerService = userAnswerService;

    }

    @GetMapping("/take/{quizId:\\d+}/question/{index:\\d+}")
    public String takeQuiz(@PathVariable("quizId") Long quizId,
            @PathVariable("index") int index,
            @RequestParam(name = "score", defaultValue = "0") int score,
            Model model, @ModelAttribute("user") User currentUser) {
        if (index == 0) {
            userAnswerService.deleteByUserIdAndQuizId(currentUser.getUserId(), quizId);
        }
        Quiz quiz = quizService.getQuizById(quizId);
        List<Question> questions = questionService.getQuestionsByQuizId(quizId);

        if (index >= questions.size()) {
            model.addAttribute("finalScore", score);
            model.addAttribute("totalQuestions", questions.size());
            model.addAttribute("quizTitle", quiz.getTitle());
            User user = currentUser;
            List<UserAnswer> userAnswers = userAnswerService.getUserAnswersByUserIdAndQuizId(user.getUserId(), quizId);
               long correctAnswers = userAnswers.stream()
            .filter(ua -> ua.getChoice().isCorrect())
            .count();
            for (UserAnswer ua : userAnswers) {
                ua.getQuestion().getChoices().size(); // force load
                Optional<Choice> correctChoice = ua.getQuestion().getChoices().stream()
                        .filter(Choice::isCorrect)
                        .findFirst();
                // Giả sử bạn có thêm biến trong model hoặc map tạm cho đáp án đúng
                ua.setCorrectChoiceText(correctChoice.map(Choice::getChoiceText).orElse("Không có đáp án đúng"));
            }
            QuizResult quizResult = new QuizResult();
            quizResult.setUser(user);
            quizResult.setQuiz(quiz);
            quizResult.setScore((float) score);
            quizResult.setTotalQuestions(questions.size());
            quizResult.setCorrectAnswers((int) correctAnswers);
            quizResult.setTakenAt(LocalDateTime.now());

            quizResultService.saveQuizResult(quizResult);

            model.addAttribute("userAnswers", userAnswers);
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

    @PostMapping("/take/{quizId:\\d+}/question/{index:\\d+}/answer")
    public String submitAnswer(@PathVariable("quizId") Long quizId,
            @PathVariable("index") int index,
            @RequestParam("score") int score,
            @RequestParam("choiceId") Long choiceId,
            @RequestParam(value = "timeout", required = false) Boolean timeout,
            @ModelAttribute("user") User currentUser) {

        int updatedScore = score;

        // Lấy thông tin các entity cần thiết
        User user = currentUser;
        Quiz quiz = quizService.getQuizById(quizId);
        Question question = questionService.getQuestionByIndex(quizId, index); // hoặc
                                                                               // getQuestionsByQuizId().get(index);
        Choice choice = choiceService.getChoiceById(choiceId);

        // Kiểm tra đúng/sai
        if (Boolean.TRUE.equals(timeout)) {
            // Nếu timeout không cộng điểm
        } else if (choice.isCorrect()) {
            updatedScore++;
        }

        // Lưu vào user_answers
        userAnswerService.saveUserAnswer(user, quiz, question, choice);

        // Chuyển sang câu tiếp theo
        int nextIndex = index + 1;
        return "redirect:/quizzes/take/" + quizId + "/question/" + nextIndex + "?score=" + updatedScore;
    }

}
