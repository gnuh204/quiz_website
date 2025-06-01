package com.example.qiuzweb.service;

import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.domain.Dto.UserDTO;
import com.example.qiuzweb.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userResitory;

    public UserService(UserRepository userResitory) {
        this.userResitory = userResitory;
    }

    public boolean usernameexists(String username){
         return userResitory.findByUsername(username).isPresent();
    }

    public boolean emailexists(String email){
        return userResitory.findByEmail(email).isPresent();
    }

    public User registerUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(userDTO.getPassword());
        user.setRole(User.Role.USER);
        return userResitory.save(user);
    }

}
