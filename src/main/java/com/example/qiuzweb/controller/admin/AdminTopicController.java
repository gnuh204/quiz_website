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
@RequestMapping("/topics")
public class AdminTopicController {

  
    private final CategoryService categoryService;

    public AdminTopicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "POST client/layout/createnewcategory"; 
    }

    @PostMapping("/save")
public String saveTopic(@RequestParam("title") String title,
                        @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

    String fileName = imageFile.getOriginalFilename();

    // Thư mục images
    Path imagesPath = Paths.get("src/main/resources/static/images/");
    if (!Files.exists(imagesPath)) {
        Files.createDirectories(imagesPath);
    }
    Path imagesFilePath = imagesPath.resolve(fileName);
    Files.copy(imageFile.getInputStream(), imagesFilePath, StandardCopyOption.REPLACE_EXISTING);

    // // Thư mục uploads
    // Path uploadsPath = Paths.get("src/main/resources/static/uploads/");
    // if (!Files.exists(uploadsPath)) {
    //     Files.createDirectories(uploadsPath);
    // }
    // Path uploadsFilePath = uploadsPath.resolve(fileName);
    // // Lấy lại InputStream vì fileInputStream chỉ đọc được 1 lần
    // // Nên phải đọc lại từ MultipartFile
    // Files.copy(imageFile.getInputStream(), uploadsFilePath, StandardCopyOption.REPLACE_EXISTING);

    // Tạo và lưu category, URL vẫn lưu đến images
    Category topic = new Category();
    topic.setName(title);
    topic.setImageUrl("/images/" + fileName);

    categoryService.saveCategory(topic);

    return "redirect:/topics/list";
}
}