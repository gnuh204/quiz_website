package com.example.qiuzweb.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.domain.Dto.UserDTO;
import com.example.qiuzweb.repository.UserResitory;

@Service
public class UserService {
    private final UserResitory userResitory;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserResitory userResitory , PasswordEncoder passwordEncoder) {
        this.userResitory = userResitory;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameexists(String username){
         return userResitory.findByUsername(username).isPresent();
    }

    public boolean emailexists(String email){
        return userResitory.findByEmail(email).isPresent();
    }
    public User findByEmail(String email){
        return this.userResitory.findByEmail(email).orElse(null);
    }

    public User registerUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(User.Role.USER);
        return userResitory.save(user);
    }

public User registerUserAdmin(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(userDTO.getPassword());
        // user.setRole(User.Role.USER);
        user.setRole(userDTO.getRole());
        return userResitory.save(user);
    }
}
