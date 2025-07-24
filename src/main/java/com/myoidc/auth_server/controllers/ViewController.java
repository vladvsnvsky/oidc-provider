package com.myoidc.auth_server.controllers;

import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.models.UserEntity;
import com.myoidc.auth_server.services.QuestionService;
import com.myoidc.auth_server.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    private final QuestionService questionService;
    private final UserService userService;

    public ViewController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @GetMapping("/questions")
    public String showQuestions(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "1") int size,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions = questionService.getQuestions(pageable);
        model.addAttribute("page", questions.map(Question::toDTO));
        return "questions";
    }


    @GetMapping("/questions/create")
    public String showCreateQuestionPage() {
        // Thymeleaf will look for templates/create_question.html
        return "create_question";
    }

    @GetMapping("/questions/update")
    public String updateQuestionPage(@RequestParam long questionId, Model model){
        try{
            QuestionDTO dto = questionService.getQuestionById(questionId);
            model.addAttribute("id", questionId);
            model.addAttribute("question", dto);
            return "update_question";
        }catch (Exception err){
            model.addAttribute("message", err.getMessage());
            return "error";
        }

    }

    @GetMapping("/users/create")
    public String showCreateUserPage(){
       return "create_users";
    }

    @GetMapping("/users")
    public String showUsersPage(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "1") int size,
                                Model model){
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> questions = userService.getUsers(pageable, query);
        model.addAttribute("page", questions.map(UserEntity::toDTO));
        return "users";
    }
}
