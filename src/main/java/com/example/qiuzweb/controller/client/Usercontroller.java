package com.example.qiuzweb.controller.client;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.qiuzweb.domain.Dto.UserDTO;
import com.example.qiuzweb.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

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
    public String Showsignup( Model model) {
        model.addAttribute("user", new UserDTO());
        return "client/signup"; 
    }
    @PostMapping("/register")
    public String processRegister( @ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
        return "client/signup";
            }
        if(userService.usernameexists(userDTO.getUsername())){
            result.rejectValue("username", null, "Tên đã tồn tại");
            return "client/signup";
        }
         if(userService.emailexists(userDTO.getEmail())){
            result.rejectValue("email", null,"email đã tồn tại");
           return "client/signup";
        }
        userService.registerUser(userDTO); 
        return "redirect:/login";
    }
    
    @GetMapping("/forgotpassword")
    public String forgotpassword() {
        return "client/forgotpassword"; 
    }
    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        UserDTO user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);

        return "client/InforFragment";
    }

}

