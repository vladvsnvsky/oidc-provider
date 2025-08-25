package com.myoidc.auth_server.models;

import com.myoidc.auth_server.dto.ExamDTO;
import com.myoidc.auth_server.dto.ExamQuestionDTO;
import com.myoidc.auth_server.models.enums.AnswerStatus;
import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.*;

@Entity
public class Exam {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // an exam belongs to a user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // use join entity to carry per-exam state
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExamQuestion> questions = new HashSet<>();

    private boolean finished = false;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Exam(){}

    public Exam(Long id, UserEntity user, Set<ExamQuestion> questions) {
        this.id = id;
        this.user = user;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<ExamQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<ExamQuestion> questions) {
        this.questions = questions;
    }

    public ExamDTO toDTO(){
        ExamDTO dto = new ExamDTO();
        dto.setId(id);
        dto.setUserEmail(user.getEmail());
        dto.setUserId(user.getId().toString());
        List<ExamQuestionDTO> eqDto = new ArrayList<>();
        questions.forEach(q ->{
            eqDto.add(q.toDTO());
        });
        dto.setQuestions(eqDto);
        return dto;
    }


    public void updateExamQuestion(ExamQuestion fExamQuestion) {
        this.questions.removeIf(q -> Objects.equals(q.getId(), fExamQuestion.getId()));
        this.questions.add(fExamQuestion);
    }
}
