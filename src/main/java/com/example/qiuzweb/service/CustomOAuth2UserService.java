package com.example.qiuzweb.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.User;
import com.example.qiuzweb.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Gọi class cha để lấy dữ liệu từ Google/Facebook/GitHub
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google, facebook, github

        // Các thuộc tính chung
        final String[] email = new String[1];
        final String[] name = new String[1];
        final String[] imageUrl = new String[1];

        // Phân biệt từng provider
        if (registrationId.equals("google")) {
            email[0] = oauth2User.getAttribute("email");
            name[0] = oauth2User.getAttribute("name");
            imageUrl[0] = oauth2User.getAttribute("picture");
        } else if (registrationId.equals("facebook")) {
            email[0] = oauth2User.getAttribute("email");
            name[0] = oauth2User.getAttribute("name");
            Map<String, Object> pictureObj = oauth2User.getAttribute("picture");
            if (pictureObj != null) {
                Map<String, Object> data = (Map<String, Object>) pictureObj.get("data");
                imageUrl[0] = (String) data.get("url");
            }
        } else if (registrationId.equals("github")) {
            email[0] = oauth2User.getAttribute("email");
            name[0] = oauth2User.getAttribute("name");
            if (name[0] == null) {
                name[0] = oauth2User.getAttribute("login"); // fallback
            }
            imageUrl[0] = oauth2User.getAttribute("avatar_url");
        }

        // Lưu hoặc cập nhật user
        User user = userRepository.findByEmail(email[0]).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email[0]);
            newUser.setUsername(name[0]);
            newUser.setImgurl(imageUrl[0]);
            newUser.setRole(User.Role.USER);
            newUser.setProvider(registrationId);
            return userRepository.save(newUser);
        });

        return new CustomOAuth2User(oauth2User, user.getRole());
    }
}
