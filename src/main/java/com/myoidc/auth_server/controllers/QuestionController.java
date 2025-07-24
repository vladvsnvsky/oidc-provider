package com.myoidc.auth_server.controllers;

import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.services.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/questions")
    public ResponseEntity<QuestionDTO> saveQuestion(@RequestBody QuestionDTO questionDTO) {
        try {
            Question saved = questionService.saveQuestion(Question.fromDTO(questionDTO));
            QuestionDTO result = saved.toDTO();
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestionById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(
            @PathVariable Long id,
            @RequestBody QuestionDTO questionDTO) {
        try {
            Question updated = questionService.updateQuestion(id, questionDTO);
            return ResponseEntity.ok(updated.toDTO());
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/questions")
    public ResponseEntity<Page<QuestionDTO>> getQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions = questionService.getQuestions(pageable);
        Page<QuestionDTO> dtoPage = questions.map(Question::toDTO);
        return ResponseEntity.ok(dtoPage);
    }




}
