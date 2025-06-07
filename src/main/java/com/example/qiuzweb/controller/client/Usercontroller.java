package com.example.qiuzweb.controller.client;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.domain.Dto.UserDTO;
import com.example.qiuzweb.service.CategoryService;
import com.example.qiuzweb.service.UserService;

import jakarta.validation.Valid;

@Controller
public class Usercontroller {
    private final UserService userService;
    private final CategoryService categoryService;
  

    public Usercontroller(UserService userService, PasswordEncoder passwordEncoder,
            CategoryService categoryService) {
        this.categoryService = categoryService;
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
        model.addAttribute("categorys", categoryService.allCategory());
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
        model.addAttribute("users", new UserDTO());
        return "client/signup";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("users") @Valid UserDTO userDTO, BindingResult result, Model model) {

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

    // Doi mat khau
    @PostMapping("/setting/password")
    public String updatePassword(
            @RequestParam("email") String email,
            @RequestParam("newpass") String newPass,
            @RequestParam("renewpass") String renewPass,
           
            RedirectAttributes redirectAttributes) {
        if (!newPass.equals(renewPass)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu không khớp.");
            return "redirect:/Setting"; // quay lại trang setting
        }
        if (newPass.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
            return "redirect:/Setting";
        }

        boolean success = userService.updatePasswordByEmail(email, newPass);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Đã cập nhật mật khẩu thành công.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng.");
        }
        return "redirect:/Setting";
    }

    // chinh su ho so
    @GetMapping("/Setprofile")
    public String setprofile(Model model) {
        return "client/setprofileFragment";
    }

}
