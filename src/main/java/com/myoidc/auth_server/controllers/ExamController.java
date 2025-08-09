package com.myoidc.auth_server.controllers;
import com.myoidc.auth_server.dto.*;
import com.myoidc.auth_server.models.ApiResponse;
import com.myoidc.auth_server.models.Exam;
import com.myoidc.auth_server.services.ExamService;
import com.myoidc.auth_server.services.QuestionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/api/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }


    @PostMapping("/start")
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
                    "failed"
            );
            return ResponseEntity.ok(apiResp);
        }

    }

}
