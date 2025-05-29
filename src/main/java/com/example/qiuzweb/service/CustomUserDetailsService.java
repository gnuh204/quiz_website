package com.example.qiuzweb.service;


import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.qiuzweb.domain.User;

public class CustomUserDetailsService implements UserDetailsService{

    private final UserService userService;
    

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findByEmail(username);
        if(user == null){
             System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User not found");
        }
         System.out.println("User found: " + user.getEmail());
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPasswordHash(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE"+user.getRole())));
    }
    
}   
