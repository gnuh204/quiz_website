package com.example.qiuzweb.config;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.repository.UserRepository;

@ControllerAdvice
public class GlobalModelAttributes {

    private final UserRepository userRepository;

    public GlobalModelAttributes(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

@ModelAttribute
public void addCurrentUserToModel(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
        String email = auth.getName();  // email chứ không phải username
        Optional<User> userOpt = userRepository.findByEmail(email);
        userOpt.ifPresent(user -> model.addAttribute("currentUser", user));
    }
    }
}
