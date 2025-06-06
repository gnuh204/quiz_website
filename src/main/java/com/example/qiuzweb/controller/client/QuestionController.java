package com.example.qiuzweb.controller.client;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.qiuzweb.domain.Category;
import com.example.qiuzweb.domain.Choice;
import com.example.qiuzweb.domain.Question;
import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.service.CategoryService;
import com.example.qiuzweb.service.QuestionService;
import com.example.qiuzweb.service.QuizService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;
   
    
    public QuestionController(QuestionService questionService, QuizService quizService,
                             CategoryService categoryService) {
       
        this.questionService = questionService;
        this.quizService = quizService;
    }
    
@GetMapping("/questions/add/{quizId}")
public String addQuestionForm(@PathVariable("quizId") Long quizId, Model model) {
    Quiz quiz = quizService.getQuizById(quizId);

    Question question = new Question();
    question.setQuiz(quiz);

    for (int i = 0; i < 4; i++) {
        question.getChoices().add(new Choice());
    }

    model.addAttribute("quiz", quiz);
    model.addAttribute("questionForm", question);

    return "client/question/questionaddform";
}
@PostMapping("/questions/add/{quizId}")
public String saveQuestion(@PathVariable("quizId") Long quizId,
                           @ModelAttribute("questionForm") Question questionForm) {
    questionService.saveQuestionWithChoices(questionForm, quizId);
    return "redirect:/quizzes/detail/" + quizId;
}

}

