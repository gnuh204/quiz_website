package com.example.qiuzweb.domain.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.service.validator.PasswordMatches;

@PasswordMatches
public class UserDTO {

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    // Xác nhận mật khẩu chỉ bắt buộc nếu là USER
    @NotBlank(message = "Xác nhận mật khẩu không được để trống", groups = NormalUser.class)
    private String confirmPassword;

    // Thêm role để xác định là ADMIN hay USER
    // private String role = "USER"; // mặc định là USER
    // private String role;
private User.Role role;
    // Getters và Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // public String getRole() {
    //     return role;
    // }

    // public void setRole(String role) {
    //     this.role = role;
    // }
    //  public interface NormalUser {}
    public User.Role getRole() {
    return role;
}

public void setRole(User.Role role) {
    this.role = role;
}
}
