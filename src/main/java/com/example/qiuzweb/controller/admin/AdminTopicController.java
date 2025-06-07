package com.example.qiuzweb.controller.admin;

import com.example.qiuzweb.service.CategoryService;
import com.example.qiuzweb.service.QuizService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import com.example.qiuzweb.domain.Category;
import com.example.qiuzweb.domain.Quiz;


@Controller
@RequestMapping("admin/topics")
public class AdminTopicController {

  
    private final CategoryService categoryService;
    private final QuizService quizService;

    public AdminTopicController(CategoryService categoryService, QuizService quizService) {
        this.quizService = quizService;
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

    @GetMapping("/list")
    public String listTopics(Model model) {
        model.addAttribute("categorys", categoryService.allCategory());
    
        return "admin/Quiz-mannagement";
    }
    @PostMapping("/delete/{id:\\d+}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Xoá chủ đề thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không thể xoá chủ đề.");
        }
        return "redirect:/admin/topics/list";
    }
    @GetMapping("/quiz/{id:\\d+}")
public String showCategoryDetail(@PathVariable("id") Long id, Model model) {
    Category category = categoryService.getCategoryById(id);
    List<Quiz> quizzes = quizService.getQuizzesByCategoryId(id);
    model.addAttribute("category", category);
    model.addAttribute("quizzes", quizzes);
    return "admin/quizlist-mannagement";
}
@PostMapping("/quizzes/delete/{quizId}/{categoryId}")
public String deleteQuiz(@PathVariable("quizId") Long quizId, @PathVariable("categoryId") Long categoryId) {
    quizService.deleQuizQuizById(quizId);
    return "redirect:/admin/topics/quiz/"+categoryId; 
}
}
