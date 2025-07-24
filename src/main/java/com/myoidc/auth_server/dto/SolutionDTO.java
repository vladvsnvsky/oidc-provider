package com.myoidc.auth_server.dto;


public class SolutionDTO {
    private String optionId;         // e.g., "a", "b", "c", "d"
    private String text;
    private boolean correct;

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
}


