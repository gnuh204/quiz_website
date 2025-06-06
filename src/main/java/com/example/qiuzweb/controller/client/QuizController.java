package com.example.qiuzweb.controller.client;

import java.io.IOException;
import java.util.List;
import com.example.qiuzweb.domain.Category;
import com.example.qiuzweb.domain.Question;
import com.example.qiuzweb.domain.Quiz;
import com.example.qiuzweb.domain.QuizComment;
import com.example.qiuzweb.domain.User;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.qiuzweb.service.CategoryService;
import com.example.qiuzweb.service.QuestionService;
import com.example.qiuzweb.service.QuizCommentService;
import com.example.qiuzweb.service.QuizService;


@Controller
public class QuizController {

    private final QuizService quizService;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final QuizCommentService commentService;
    public QuizController(QuizService quizService, CategoryService categoryService,
            QuestionService questionService, QuizCommentService questionCommentService) {
        this.commentService = questionCommentService;
        this.questionService = questionService;
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
                       Authentication authentication) {
    quizService.saveQuiz(title, description, categoryId, timeLimitMinutes, imageFile, authentication);
    return "redirect:/Library";
}

    //thu vien Quiz
    @GetMapping("/Library")
   public String showUserQuizzes(Model model, @ModelAttribute("user") User currentUser) {
    if (currentUser == null) {
        return "redirect:/login"; // Hoặc xử lý khi chưa đăng nhập
    }
    List<Quiz> quizzes = quizService.getQuizzesByCurrentUser(currentUser);
    model.addAttribute("quizzes", quizzes);
    return "client/librarFragment";
    }

    @PostMapping("/quizzes/{id:\\d+}/delete")
public String deleteQuizString(@PathVariable("id") Long id, @ModelAttribute("user") User currentUser) {
    quizService.deleteQuiz(id, currentUser); // Xóa quiz theo ID và người dùng hiện tại
    return "redirect:/Library"; // Chuyển hướng về trang thư viện sau khi xóa quiz
}


    // xem chi tiet Quiz
    @GetMapping("/quizzes/detail/{id:\\d+}") 
public String viewQuizDetails(@PathVariable("id") Long id, Model model) {
    Quiz quiz = quizService.getQuizById(id); 
    List<Question> questions = questionService.getQuestionsByQuizId(id);

    model.addAttribute("quiz", quiz);
    model.addAttribute("questions", questions);

    return "client/quizdetailFragment";
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
        Path uploadPath = Paths.get("src/main/resources/static/images/topicimages");
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
    
    // Hien thị danh sách quiz cua category
@GetMapping("/category/{id:\\d+}")
public String showCategoryDetail(@PathVariable("id") Long id, Model model) {
    Category category = categoryService.getCategoryById(id);
    List<Quiz> quizzes = quizService.getQuizzesByCategoryId(id);
    model.addAttribute("category", category);
    model.addAttribute("quizzes", quizzes);
    return "client/quizlistFragment";
}

// Hien thi danh gia
@GetMapping("/quizzes/rate/{id:\\d+}")
public String rateQuiz(@PathVariable("id") Long id, Model model, @ModelAttribute("user") User currentUser) {
    Quiz quiz = quizService.getQuizById(id);
    model.addAttribute("quiz", quiz);
    List<QuizComment> comments = commentService.getCommentsByQuiz(quiz);
        model.addAttribute("comments", comments);
        model.addAttribute("user", currentUser);
    return "client/quesinforFragment";

}
@PostMapping("/quizzes/{id:\\d+}/comments")
    public String postComment(@PathVariable("id") Long id,
                              @RequestParam("comment") String commentText,
                              @ModelAttribute("user") User currentUser) {
        Quiz quiz = quizService.getQuizById(id);
        commentService.addComment(quiz, currentUser, commentText);
        return "redirect:/quizzes/rate/" + id; 
    }

    @GetMapping("/comments/delete/{id:\\d+}")
public String deleteComment(@PathVariable("id") Long commentId, @ModelAttribute("user") User currentUser) {
    commentService.deleteIfOwner(commentId, currentUser);
    return "redirect:/quizzes/rate/" + commentService.findQuizIdByCommentId(commentId);
}

}
