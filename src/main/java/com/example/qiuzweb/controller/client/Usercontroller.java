package com.example.qiuzweb.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.qiuzweb.domain.Dto.UpdateUserDTO;
import com.example.qiuzweb.domain.Dto.UserDTO;
import com.example.qiuzweb.service.UserService;
import com.example.qiuzweb.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Valid;

@Controller
public class Usercontroller {
    private final UserService userService;

private final PasswordEncoder passwordEncoder;

public Usercontroller(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
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
public String setting(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName(); 

    User user = userService.findByEmail(email);
    if (user == null) {
        model.addAttribute("errorMessage", "Không tìm thấy tài khoản!");
        return "client/settingFragment";
    }

    UpdateUserDTO updateUserDTO = new UpdateUserDTO();
    updateUserDTO.setUsername(user.getUsername());
    updateUserDTO.setEmail(user.getEmail());
    model.addAttribute("user", updateUserDTO);
    return "client/settingFragment";
}

@PostMapping("/Setting")
public String updateUserInfo(@ModelAttribute("user") UpdateUserDTO userDTO, BindingResult result, Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String currentEmail = auth.getName();

    User currentUser = userService.findByEmail(currentEmail);
    if (currentUser == null) {
        model.addAttribute("errorMessage", "Không tìm thấy người dùng!");
        return "client/settingFragment";
    }

    if (!currentUser.getEmail().equals(userDTO.getEmail()) && userService.emailexists(userDTO.getEmail())) {
        result.rejectValue("email", "error.user", "Email đã tồn tại.");
        return "client/settingFragment";
    }

    if (!currentUser.getUsername().equals(userDTO.getUsername()) && userService.usernameexists(userDTO.getUsername())) {
        result.rejectValue("username", "error.user", "Tên tài khoản đã tồn tại.");
        return "client/settingFragment";
    }



    currentUser.setUsername(userDTO.getUsername());
    currentUser.setEmail(userDTO.getEmail());
    userService.save(currentUser);

    model.addAttribute("user", userDTO);
    model.addAttribute("successMessage", "Cập nhật thông tin thành công!");
    return "client/settingFragment";
}

//Doi mat khau
@PostMapping("/setting/password")
public String updatePassword(@RequestParam("email") String email,
                             @RequestParam("newpass") String newpass,
                             @RequestParam("renewpass") String renewpass,
                             Model model) {

    User user = userService.findByEmail(email);

    if (user == null) {
        model.addAttribute("errorMessage", "Không tìm thấy người dùng!");
        return "client/settingFragment";
    }

    UserDTO userDTO = new UserDTO();
    userDTO.setEmail(user.getEmail());
    userDTO.setUsername(user.getUsername());
    model.addAttribute("user", userDTO);

    if (!newpass.equals(renewpass)) {
        model.addAttribute("passwordError", "Mật khẩu nhập lại không khớp!");
        return "client/settingFragment";
    }

    if (newpass.length() < 6) {
        model.addAttribute("passwordError", "Mật khẩu phải có ít nhất 6 ký tự!");
        return "client/settingFragment";
    }

    user.setPasswordHash(passwordEncoder.encode(newpass));
    userService.save(user);

    model.addAttribute("successMessage", "Cập nhật mật khẩu thành công!");
    return "client/settingFragment";
}



    // chinh su ho so
    @GetMapping("/Setprofile")
    public String setprofile(Model model) {
        model.addAttribute("user", new UserDTO());
        return "client/setprofileFragment";
    }
}
