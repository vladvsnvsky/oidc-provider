package com.myoidc.auth_server.models;

import com.myoidc.auth_server.dto.SolutionDTO;
import jakarta.persistence.*;

@Entity
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // can still use "a", "b", "c", "d" as primary key, but consider using a Long id in practice

    private String optionId; // <-- "a", "b", "c", "d"

    private String text;

    private boolean correct;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public SolutionDTO toDTO() {
        SolutionDTO dto = new SolutionDTO();
        dto.setOptionId(this.getOptionId());
        dto.setText(this.getText());
        dto.setCorrect(this.isCorrect());
        return dto;
    }
}

