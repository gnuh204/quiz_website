package com.example.qiuzweb.controller.admin;

import com.example.qiuzweb.domain.TopicEntity;
import com.example.qiuzweb.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Controller
@RequestMapping("/admin/topics")
public class AdminTopicController {

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping("/create")
    public String showCreateForm() {
        return "POST client/layout/createnewcategory";  // trang HTML tạo chủ đề
    }

    @PostMapping("/save")
    public String saveTopic(@RequestParam("title") String title,
                            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        String fileName = imageFile.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/images/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        TopicEntity topic = new TopicEntity();
        topic.setTitle(title);
        topic.setImageName(fileName);
        topicRepository.save(topic);

        return "redirect:/admin/topics/list";
    }

    @GetMapping("/list")
    public String listTopics(Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        return "admin/topic-list";
    }
}
