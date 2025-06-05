package com.example.qiuzweb.controller.client;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.service.UserService;

@ControllerAdvice
public class GlobalModelAttributeAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User globalUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        String email = null;

        if (principal instanceof OAuth2User oauth2User) {
            email = oauth2User.getAttribute("email");
        } else if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        }

        if (email != null) {
            return userService.findByEmail(email);
        }

        return null;
    }
}
