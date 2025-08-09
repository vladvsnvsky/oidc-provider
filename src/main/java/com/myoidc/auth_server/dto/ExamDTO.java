package com.myoidc.auth_server.dto;


import java.util.ArrayList;
import java.util.List;

// ExamDTO.java
public class ExamDTO {
    private Long id;

    // keep user data minimal in the DTO
    private String userId;     // UUID as string to avoid coupling
    private String userEmail;

    private List<ExamQuestionDTO> questions = new ArrayList<>();

    public ExamDTO() {}

    public ExamDTO(Long id, String userId, String userEmail, List<ExamQuestionDTO> questions) {
        this.id = id;
        this.userId = userId;
        this.userEmail = userEmail;
        this.questions = questions;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public List<ExamQuestionDTO> getQuestions() { return questions; }
    public void setQuestions(List<ExamQuestionDTO> questions) { this.questions = questions; }
}


