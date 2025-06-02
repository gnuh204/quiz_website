package com.example.qiuzweb.controller.admin;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class UserAdcontroller {

  private final UserService userService;
    
    public UserAdcontroller(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("username", "Admin");
        return "admin/index3";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("username", "Admin");
        model.addAttribute("totalUsers", 120);
        model.addAttribute("totalQuizzes", 45);
        model.addAttribute("userPercent", 75);
        model.addAttribute("quizPercent", 55);
        return "admin/dashboard";
    }

    @GetMapping("/admin/accounts")
public String accountManagement(Model model) {
    List<User> users = userService.findAllUser();
    model.addAttribute("users", users);
    return "admin/account-management";
}


    @GetMapping("/admin/quizzes")
    public String quizManagement(Model model) {
        return "admin/quiz-management";
    }

    @GetMapping("/admin/account/create")
    public String createAccountForm() {
        return "admin/create-account";
    }

    @GetMapping("/admin/account/add")
    public String addAccountForm() {
        return "admin/add-account";
    }

    @GetMapping("/admin/question/add")
    public String addQuestionForm() {
        return "admin/Cauhoi";
    }
}
