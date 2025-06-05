package com.example.qiuzweb.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.domain.Dto.UserDTO;
import com.example.qiuzweb.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
}

    public boolean usernameexists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean emailexists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }


    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(User.Role.USER);
        user.setProvider("local");
        return userRepository.save(user);
    }

    public User registerUserAdmin(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
         user.setProvider("local");
        return userRepository.save(user);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

public void deleteUserById(Long id) {
    userRepository.deleteById(id);
}

public boolean updatePasswordByEmail(String email, String newPassword) {
        return userRepository.findByEmail(email)
            .map(user -> {
                user.setPasswordHash(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return true;
            })
            .orElse(false);
    }
}
