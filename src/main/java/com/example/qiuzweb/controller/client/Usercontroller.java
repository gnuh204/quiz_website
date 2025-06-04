package com.example.qiuzweb.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.qiuzweb.domain.Dto.UserDTO;
import com.example.qiuzweb.service.UserService;

import jakarta.validation.Valid;

@Controller
public class Usercontroller {
    private final UserService userService;

    public Usercontroller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String Showhomeclient() {
        return "client/topicfragment";
    }

    @GetMapping("/login")
    public String login() {
        return "client/login";
    }

    @GetMapping("/register")
    public String Showsignup(Model model) {
        model.addAttribute("user", new UserDTO());
        return "client/signup";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "client/signup";
        }
        if (userService.usernameexists(userDTO.getUsername())) {
            result.rejectValue("username", "Tên đã tồn tại");
            return "client/signup";
        }
        if (userService.emailexists(userDTO.getEmail())) {
            result.rejectValue("email", "email đã tồn tại");
            return "client/signup";
        }
        userService.registerUser(userDTO);
        return "redirect:/login";
    }

    @GetMapping("/forgotpassword")
    public String forgotpassword() {
        return "client/forgotpassword";
    }

    @GetMapping("/Setting")
    public String setting() {
        return "client/settingFragment";
    }

    // chinh su ho so
    @GetMapping("/Setprofile")
    public String setprofile(Model model) {
        model.addAttribute("user", new UserDTO());
        return "client/setprofileFragment";
    }
   
}
