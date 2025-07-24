package com.myoidc.auth_server.dto;

public class ExamResultDTO {
    private boolean passed;
    private double score;
    private String examId;
    private String studentId;

    public ExamResultDTO(boolean passed, double score, String examId, String studentId) {
        this.passed = passed;
        this.score = score;
        this.examId = examId;
        this.studentId = studentId;
    }

    // Getters
    public boolean isPassed() {
        return passed;
    }

    public double getScore() {
        return score;
    }

    public String getExamId() {
        return examId;
    }

    public String getStudentId() {
        return studentId;
    }
}

