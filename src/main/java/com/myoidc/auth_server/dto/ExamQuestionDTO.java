package com.myoidc.auth_server.dto;

import com.myoidc.auth_server.models.enums.AnswerStatus;

public class ExamQuestionDTO {
    public ExamQuestionDTO(Long id, Long examId, QuestionDTO questionDTO, boolean answered, AnswerStatus correct) {
        this.id = id;
        this.examId = examId;
        this.question = questionDTO;
        this.answered = answered;
        this.correct = correct;
    }

    private Long id;
    private Long examId;
    private QuestionDTO question;
    private boolean answered;
    private AnswerStatus correct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionDTO getQuestionDTO() {
        return question;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public AnswerStatus getCorrect() {
        return correct;
    }

    public void setCorrect(AnswerStatus correct) {
        this.correct = correct;
    }
}

