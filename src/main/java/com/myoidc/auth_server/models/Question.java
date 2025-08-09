package com.myoidc.auth_server.models;

import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.dto.SolutionDTO;
import com.myoidc.auth_server.models.enums.QuestionType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solution> solutions;

    private QuestionType type; // "single" or "multiple"

    private String category;

    private String imageUrl;


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

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public QuestionDTO toDTO() {
        QuestionDTO dto = new QuestionDTO();
        dto.setText(this.getText());
        dto.setType(this.getType());
        dto.setCategory(this.getCategory());
        dto.setImageUrl(this.getImageUrl());

        // Map solutions to SolutionDTO
        List<SolutionDTO> solutionDTOs = new ArrayList<>();
        if (this.getSolutions() != null) {
            for (Solution solution : this.getSolutions()) {
                solutionDTOs.add(solution.toDTO());
            }
        }
        dto.setSolutions(solutionDTOs);

        return dto;
    }

    public static Question fromDTO(QuestionDTO dto){
        Question question = new Question();
        question.setText(dto.getText());
        question.setType(dto.getType());
        question.setCategory(dto.getCategory());
        question.setImageUrl(dto.getImageUrl());

        List<Solution> solutionList = new ArrayList<>();
        if (dto.getSolutions() != null) {
            for (SolutionDTO solutionDTO : dto.getSolutions()) {
                Solution solution = new Solution();
                solution.setOptionId(solutionDTO.getOptionId());
                solution.setText(solutionDTO.getText());
                solution.setQuestion(question);
                solutionList.add(solution);
            }
        }
        question.setSolutions(solutionList);

        return question;
    }

}
