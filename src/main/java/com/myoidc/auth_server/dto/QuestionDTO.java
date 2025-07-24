package com.myoidc.auth_server.dto;

import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.models.Solution;

import java.util.ArrayList;
import java.util.List;

public class QuestionDTO {
    private String text;
    private List<SolutionDTO> solutions;
    private String type;      // "single" or "multiple"
    private String category;
    private String imageUrl;

    // Getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<SolutionDTO> getSolutions() { return solutions; }
    public void setSolutions(List<SolutionDTO> solutions) { this.solutions = solutions; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }


}


