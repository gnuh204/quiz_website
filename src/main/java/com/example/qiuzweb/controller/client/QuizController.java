package com.example.qiuzweb.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class QuizController {

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
    
}
