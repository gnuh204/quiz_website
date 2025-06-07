package com.example.qiuzweb.controller.admin;

import com.example.qiuzweb.domain.Category;
import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.service.CategoryService;
import com.example.qiuzweb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller

public class UserAdcontroller {

  private final UserService userService;
  private final CategoryService categoryService;
    public UserAdcontroller(UserService userService, CategoryService categoryService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @GetMapping("/admin")
    public String adminHome(Model model) {
        return "admin/index3";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        List<User> users = userService.findAllUser();
        int totalUsers = users.size();
        model.addAttribute("totalUsers", totalUsers);
        List<Category> categories = categoryService.findAllCategories();
        int totalCategories = categories.size();
        model.addAttribute("totalCategories", totalCategories);
       
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
    @PostMapping("/admin/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {
    userService.deleteUserById(userId);
    return "redirect:/admin/accounts";
}
}
