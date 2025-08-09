package com.myoidc.auth_server.controllers;

import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.models.ApiResponse;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.services.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> saveQuestion(@RequestBody QuestionDTO questionDTO) {
        try {
            Question saved = questionService.saveQuestion(Question.fromDTO(questionDTO));
            QuestionDTO result = saved.toDTO();
            ApiResponse apiResp = new ApiResponse(
                    true,
                    result,
                    "success"
            );
            return ResponseEntity.ok(apiResp);
        } catch (Exception ex) {
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    "Something went wrong when creating a question"
            );
            return ResponseEntity.ok(apiResp);
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse> saveQuestions(@RequestBody List<QuestionDTO> questionDTOs) {
        try {
            List<Question> questionsToSave = questionDTOs.stream()
                    .map(Question::fromDTO)
                    .collect(Collectors.toList());

            List<Question> savedQuestions = questionService.saveAllQuestions(questionsToSave);

            List<QuestionDTO> result = savedQuestions.stream()
                    .map(Question::toDTO)
                    .collect(Collectors.toList());

            ApiResponse apiResp = new ApiResponse(
                    true,
                    result,
                    "success"
            );
            return ResponseEntity.ok(apiResp);
        } catch (Exception ex) {
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    "Something went wrong saving bulk questions!"
            );
            return ResponseEntity.ok(apiResp);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestionById(id);
            ApiResponse apiResp = new ApiResponse(
                    true,
                    null,
                    "success"
            );
            return ResponseEntity.ok(apiResp);// 204 No Content
        } catch (Exception ex) {
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    "Something went wrong deleting the question!"
            );
            return ResponseEntity.ok(apiResp);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateQuestion(
            @PathVariable Long id,
            @RequestBody QuestionDTO questionDTO) {
        try {
            Question updated = questionService.updateQuestion(id, questionDTO);
            ApiResponse apiResp = new ApiResponse(
                    true,
                    updated.toDTO(),
                    "success"
            );
            return ResponseEntity.ok(apiResp);
        } catch (EntityNotFoundException ex) {
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    "Question not found in database!"
            );
            return ResponseEntity.ok(apiResp);
        } catch (Exception ex) {
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    "Something went wrong!"
            );
            return ResponseEntity.ok(apiResp);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Question> questions = questionService.getQuestions(pageable);
            Page<QuestionDTO> dtoPage = questions.map(Question::toDTO);
            ApiResponse apiResp = new ApiResponse(
                    true,
                    dtoPage,
                    "success"
            );
            return ResponseEntity.ok(apiResp);
        } catch (Exception e) {
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    "Something went wrong when calling this endpoint!"
            );
            return ResponseEntity.ok(apiResp);
        }
    }

}
