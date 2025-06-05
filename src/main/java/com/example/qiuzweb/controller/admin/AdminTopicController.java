package com.example.qiuzweb.controller.admin;

import com.example.qiuzweb.service.CategoryService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import com.example.qiuzweb.domain.Category;

@Controller
@RequestMapping("admin/topics")
public class AdminTopicController {

  
    private final CategoryService categoryService;

    public AdminTopicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

   

    @PostMapping("admin/save")
    public String saveTopic(@RequestParam("title") String title,
                            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        String fileName = imageFile.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/images/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Category topic = new Category();
        topic.setName(title);
        topic.setImageUrl(fileName);
        categoryService.saveCategory(topic);

        return "redirect:/topics/list";
    }

    @GetMapping("admin/list")
    public String listTopics(Model model) {
        model.addAttribute("categorys",categoryService.allCategory());
        return "admin/topic-list";
    }
}
