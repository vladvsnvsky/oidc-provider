package com.myoidc.auth_server.dto;

import com.myoidc.auth_server.models.Exam;
import com.myoidc.auth_server.models.Question;

import java.util.List;
import java.util.stream.Collectors;

public class ExamDTO {
    private String id = "test";
    private String studentId;
    private List<QuestionDTO> questions;

    public ExamDTO(Exam exam) {
        this.id = exam.getId(); // or "test" if hardcoded
        this.studentId = exam.getStudentId();
        this.questions = exam.getQuestions().stream()
                .map(Question::toDTO)
                .collect(Collectors.toList());
    }

    public static ExamDTO fromExam(Exam exam) {
        return new ExamDTO(exam);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }
}

