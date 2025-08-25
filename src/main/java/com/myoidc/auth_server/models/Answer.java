package com.myoidc.auth_server.models;

import com.myoidc.auth_server.dto.RegisterAnswerDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // FK lives here; Answer -> ExamQuestion (no cascade back)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_question_id", nullable = false, unique = true)
    private ExamQuestion examQuestion;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResponseModel> responses = new ArrayList<>();

    public Answer(){}

    public Answer(Long id, ExamQuestion examQuestion, List<ResponseModel> responses) {
        this.id = id;
        this.examQuestion = examQuestion;
        this.responses = responses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExamQuestion getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(ExamQuestion examQuestion) {
        this.examQuestion = examQuestion;
    }

    public List<ResponseModel> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseModel> responses) {
        this.responses = responses;
    }
}
