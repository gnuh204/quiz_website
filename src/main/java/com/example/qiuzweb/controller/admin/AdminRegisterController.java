package com.example.qiuzweb.controller.admin;

import com.example.qiuzweb.domain.Dto.UserDTO;

import com.example.qiuzweb.service.UserService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/register")
public class AdminRegisterController {

    private final UserService userService ;
    public AdminRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("adminuser", new UserDTO());
        return "admin/adminsignup";
    }

    @PostMapping
    public String registerAdmin(@ModelAttribute("adminuser") UserDTO dto) {
        userService.registerUserAdmin(dto);
        return "redirect:/login";
    }
}
