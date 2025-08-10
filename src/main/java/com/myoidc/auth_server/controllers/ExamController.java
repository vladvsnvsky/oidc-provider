package com.myoidc.auth_server.controllers;
import com.myoidc.auth_server.dto.*;
import com.myoidc.auth_server.models.ApiResponse;
import com.myoidc.auth_server.models.Exam;
import com.myoidc.auth_server.services.ExamService;
import com.myoidc.auth_server.services.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }


    @GetMapping("/start")
    public ResponseEntity<ApiResponse> startExam(Authentication authentication){
        try{

            String username = authentication.getName(); // principal username
            ExamDTO res = examService.create(username);
            ApiResponse apiResp = new ApiResponse(
                    true,
                    res,
                    "success"
            );
            return ResponseEntity.ok(apiResp);
        }catch (RuntimeException ex){
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    ex.getMessage()
            );
            return ResponseEntity.ok(apiResp);
        }

    }

    @GetMapping("/continue")
    public ResponseEntity<ApiResponse> continueExam(Authentication authentication){
        try{

            String username = authentication.getName();
            ExamDTO res = examService.getActiveExam(username);
            ApiResponse apiResp = new ApiResponse(
                    true,
                    res,
                    "success"
            );
            return ResponseEntity.ok(apiResp);
        }catch (RuntimeException ex){
            ApiResponse apiResp = new ApiResponse(
                    false,
                    null,
                    ex.getMessage()
            );
            return ResponseEntity.ok(apiResp);
        }

    }



}
