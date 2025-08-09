package com.myoidc.auth_server.dto;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.myoidc.auth_server.models.enums.AnswerStatus;

public class ExamQuestionDTO {
    private Long id;
    private Long examId;
    private QuestionDTO question;
    private boolean answered;
    private AnswerStatus correct;

    public ExamQuestionDTO(Long id, Long examId, QuestionDTO questionDTO, boolean answered, AnswerStatus correct) {
        this.id = id;
        this.examId = examId;
        this.question = questionDTO;
        this.answered = answered;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
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

