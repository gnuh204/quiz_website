package com.example.qiuzweb.controller.admin;

import com.example.qiuzweb.domain.Dto.UserDTO;

import com.example.qiuzweb.service.UserService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String registerAdmin(@ModelAttribute("adminuser") @Valid UserDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
        return "admin/adminsignup";
            }
        if(userService.usernameexists(dto.getUsername())){
            result.rejectValue("username",  "Tên đã tồn tại");
            return "admin/adminsignup";
        }
        if(userService.emailexists(dto.getEmail())){
            result.rejectValue("email", "email đã tồn tại");
           return "admin/adminsignup";
        }
        userService.registerUserAdmin(dto);
        return "admin/account-management";
    }
}
