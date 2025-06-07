package com.example.qiuzweb.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;
   
    
    public QuestionController(QuestionService questionService, QuizService quizService,
                             CategoryService categoryService) {
       
        this.questionService = questionService;
        this.quizService = quizService;
    }
    
@GetMapping("/questions/add/{quizId:\\d+}")
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
@PostMapping("/questions/add/{quizId:\\d+}")
public String saveQuestion(@PathVariable("quizId") Long quizId,
                         @ModelAttribute("questionForm") Question questionForm,
                         @RequestParam(value = "questionImage", required = false) MultipartFile imageFile,
                         RedirectAttributes redirectAttributes) {
    try {
        questionService.saveQuestionWithChoices(questionForm, quizId, imageFile);
        redirectAttributes.addFlashAttribute("success", "Câu hỏi đã được thêm thành công!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm câu hỏi: " + e.getMessage());
    }
    return "redirect:/quizzes/detail/" + quizId;
}
 @GetMapping("/questions/delete/{id:\\d+}/{quizId:\\d+}")
    public String deleteQuestion(@PathVariable("id") Long id,@PathVariable("quizId") Long quizId, RedirectAttributes redirectAttributes) {
        try {
            questionService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa câu hỏi thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Xảy ra lỗi khi xóa câu hỏi!");
        }
        return "redirect:/quizzes/detail/" + quizId; // hoặc điều hướng về trang danh sách phù hợp
    }
}

