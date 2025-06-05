package com.example.qiuzweb.controller.client;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import com.example.qiuzweb.domain.Category;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.qiuzweb.service.CategoryService;
import com.example.qiuzweb.service.QuizService;
@Controller
public class QuizController {

    private final QuizService quizService;
    private final CategoryService categoryService;
    public QuizController(QuizService quizService, CategoryService categoryService) {
        this.categoryService = categoryService;
        this.quizService = quizService;
    }

    @GetMapping("/InforFragment")
    public String resetpassword() {
        return "client/inforFragment";
    }
    // Tao Quiz
    @GetMapping("/CreateQuiz")
    public String CrteateQuiz(Model model) {
        List<Category> categories = categoryService.allCategory(); 
        model.addAttribute("categories", categories);
        return "client/createQuizFragment";
    }
     @PostMapping("/quizzes/save")
    public String saveQuiz(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("timeLimitMinutes") Integer timeLimitMinutes,
            @RequestParam("imageFile") MultipartFile imageFile,
            Principal principal) {
        quizService.saveQuiz(title, description, categoryId, timeLimitMinutes, imageFile, principal);
        return "redirect:/Library";
    }
    //thu vien Quiz
    @GetMapping("/Library")
    public String ShowLibrary() {
        return "client/librarFragment";
    }
      // tao category
    @GetMapping("/createNewCategory")
    public String getMethodName() {
        return "client/createnewcategory";
    }
    
    @PostMapping("topics/save")
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

        return "redirect:/NewCategory";
    }

    // chon Tao Category
    @GetMapping("/NewCategory")
    public String ShowNewCategory() {
        return "client/createnewfragment";
    }

    // Tien trinh lam bai
    @GetMapping("/QuizProcess")
    public String QuizProcess() {
        return "client/progressFragment";
    }
  


}
