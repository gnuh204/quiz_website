package com.example.qiuzweb.controller.client;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.qiuzweb.domain.User;
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
public String showHomeClient(Model model, Authentication authentication) {
    User user = null;

    if (authentication != null && authentication.isAuthenticated()) {
        Object principal = authentication.getPrincipal();
        String email = null;

        if (principal instanceof OAuth2User oauth2User) {
            email = oauth2User.getAttribute("email");
        } else if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        }

        if (email != null) {
            user = userService.findByEmail(email);
            System.out.println("User: " + user);
        }
    }

    model.addAttribute("user", user);
    return "client/topicfragment";
}

    @GetMapping("/login")
public String loginPage(@RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "error", required = false) String error,
                        Model model) {
    if (logout != null) {
        model.addAttribute("message", "Bạn đã đăng xuất thành công!");
    }
    if (error != null) {
        model.addAttribute("error", "Sai thông tin đăng nhập!");
    }
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
