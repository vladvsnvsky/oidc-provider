package com.myoidc.auth_server.models;

import com.myoidc.auth_server.models.enums.OptionLabel;
import jakarta.persistence.*;

@Entity(name="response")
public class ResponseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "answer_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Answer answer;

    private OptionLabel optionId;
    private boolean checked = false;

    public ResponseModel(){}

    public ResponseModel(Answer answer, OptionLabel optionId, boolean checked) {
        this.answer = answer;
        this.optionId = optionId;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public OptionLabel getOptionId() {
        return optionId;
    }

    public void setOptionId(OptionLabel optionId) {
        this.optionId = optionId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
