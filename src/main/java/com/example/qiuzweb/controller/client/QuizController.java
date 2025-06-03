package com.example.qiuzweb.controller.client;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.qiuzweb.service.QuizService;
@Controller
public class QuizController {

    private final QuizService quizService;
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/InforFragment")
    public String resetpassword() {
        return "client/inforFragment";
    }

    @GetMapping("/CreateQuiz")
    public String CrteateQuiz() {
        return "client/createQuizFragment";
    }

    @GetMapping("/Library")
    public String ShowLibrary() {
        return "client/librarFragment";
    }

    @GetMapping("/NewCategory")
    public String ShowNewCategory() {
        return "client/createnewfragment";
    }

    @GetMapping("createNewCategory")
    public String getMethodName() {
        return "client/createnewcategory";
    }

    @PostMapping("/quizzes/create")
    public String createQuiz(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("timeLimitMinutes") Integer timeLimitMinutes,
            @RequestParam("imageFile") MultipartFile imageFile,
            Principal principal) {
        quizService.saveQuiz(title, description, categoryId, timeLimitMinutes, imageFile, principal);
        return "redirect:/quizzes/list";
    }

}
