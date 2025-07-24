package com.myoidc.auth_server.models;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Exam {
    private String id = "test";
    private String studentId;
    private List<Question> questions;

    public Exam(String studentId, List<Question> questions){
//        this.id = UUID.randomUUID().toString();
        this.studentId = studentId;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
