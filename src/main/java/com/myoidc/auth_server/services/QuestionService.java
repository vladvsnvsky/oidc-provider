package com.myoidc.auth_server.services;

import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.dto.SolutionDTO;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.models.Solution;
import com.myoidc.auth_server.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestionById(Long id) {
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Long id, QuestionDTO dto) throws RuntimeException{
        Question existing = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found!"));

        // Update fields (example)
        existing.setText(dto.getText());
        existing.setType(dto.getType());
        existing.setCategory(dto.getCategory());
        existing.setImageUrl(dto.getImageUrl());

        // Handle solutions: this may mean deleting old ones and saving new ones,
        // or updating in place. Here's a simple approach:
        existing.getSolutions().clear();
        if (dto.getSolutions() != null) {
            for (SolutionDTO solutionDTO : dto.getSolutions()) {
                Solution solution = new Solution();
                solution.setOptionId(solutionDTO.getOptionId());
                solution.setText(solutionDTO.getText());
                solution.setCorrect(solutionDTO.isCorrect());
                solution.setQuestion(existing); // maintain the relationship
                existing.getSolutions().add(solution);
            }
        }

        return questionRepository.save(existing);
    }

    public Page<Question> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public QuestionDTO getQuestionById(long questionId) throws RuntimeException{
        Question existing = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found!"));

        return existing.toDTO();
    }
}
