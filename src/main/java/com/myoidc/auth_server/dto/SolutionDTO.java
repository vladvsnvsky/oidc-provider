package com.myoidc.auth_server.dto;


import com.myoidc.auth_server.models.enums.OptionLabel;

public class SolutionDTO {
    private OptionLabel optionId;         // e.g., "a", "b", "c", "d"
    private String text;

    public OptionLabel getOptionId() {
        return optionId;
    }

    public void setOptionId(OptionLabel optionId) {
        this.optionId = optionId;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

}


