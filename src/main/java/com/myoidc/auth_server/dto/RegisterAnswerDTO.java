package com.myoidc.auth_server.dto;

import java.util.List;

public class RegisterAnswerDTO {

    @com.fasterxml.jackson.annotation.JsonProperty("exam_question_id")
    private Long examQuestion;

    private List<ResponseModelDTO> responses;

    public RegisterAnswerDTO(Long examQuestion, List<ResponseModelDTO> responses) {
        this.examQuestion = examQuestion;
        this.responses = responses;
    }

    public Long getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(Long examQuestion) {
        this.examQuestion = examQuestion;
    }

    public List<ResponseModelDTO> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseModelDTO> responses) {
        this.responses = responses;
    }
}
