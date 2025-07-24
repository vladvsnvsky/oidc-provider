package com.myoidc.auth_server.models;

import java.util.List;

public class Answer {
    private String studentId;
    private String examId;
    private String questionId;
    private List<String> values;

    public Answer(String studentId, String examId, String questionId, List<String> values) {
        this.studentId = studentId;
        this.examId = examId;
        this.questionId = questionId;
        this.values = values;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
