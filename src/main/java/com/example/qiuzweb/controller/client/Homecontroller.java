package com.example.qiuzweb.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class Homecontroller {
    @GetMapping("/")
    public String Showhomeclient() {
        return "client/home";
    }
    
    @GetMapping("/login")
    public String login() {
        return "client/login"; 
    }
     @GetMapping("/signup")
    public String signup() {
        return "client/signup"; 
    }
    @GetMapping("/forgotpassword")
    public String forgotpassword() {
        return "client/forgotpassword"; 
    }
    
}
