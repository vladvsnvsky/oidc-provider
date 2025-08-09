package com.myoidc.auth_server.models;

import com.myoidc.auth_server.dto.ExamQuestionDTO;
import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.models.enums.AnswerStatus;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        name = "uq_exam_question", columnNames = {"exam_id","question_id"}))
public class ExamQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    private boolean answered;

    @Enumerated(EnumType.STRING)
    private AnswerStatus correct = AnswerStatus.NONE;

    public ExamQuestion(){}
    public ExamQuestion(Long id, Exam exam, Question question, boolean answered, AnswerStatus correct) {
        this.id = id;
        this.exam = exam;
        this.question = question;
        this.answered = answered;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
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

    public ExamQuestionDTO toDTO(){
        ExamQuestionDTO result = new ExamQuestionDTO(
                this.id,
                this.exam.getId(),
                this.question.toDTO(),
                this.answered,
                this.correct
        );
        return result;
    }

    public static ExamQuestion fromDTO(ExamQuestionDTO dto){
        return  new ExamQuestion(
                dto.getId(),
                null,
                Question.fromDTO(dto.getQuestionDTO()),
                dto.isAnswered(),
                dto.getCorrect()
        );
    }
}
