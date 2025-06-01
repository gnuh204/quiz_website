package com.example.qiuzweb.controller.admin;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserAdcontroller {

    @Autowired
    private UserRepository userRepository; // Đảm bảo tên đúng với interface

    @GetMapping("/admin")
    public String adminHome(Model model) {
        model.addAttribute("username", "Admin");
        return "index3";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("username", "Admin");
        model.addAttribute("totalUsers", 120);
        model.addAttribute("totalQuizzes", 45);
        model.addAttribute("userPercent", 75);
        model.addAttribute("quizPercent", 55);
        return "dashboard";
    }

    @GetMapping("/admin/accounts")
    public String accountManagement(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "account-management"; // file HTML nằm trong templates/
    }

    @GetMapping("/admin/quizzes")
    public String quizManagement(Model model) {
        return "quiz-management";
    }

    @GetMapping("/admin/account/create")
    public String createAccountForm() {
        return "create-account";
    }

    @GetMapping("/admin/account/add")
    public String addAccountForm() {
        return "add-account";
    }

    @GetMapping("/admin/question/add")
    public String addQuestionForm() {
        return "Cauhoi";
    }
}
