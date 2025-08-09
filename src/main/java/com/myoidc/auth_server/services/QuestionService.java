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

import java.util.*;


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
                solution.setQuestion(existing);
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

    public List<Question> getRandomQuestions(int quantity) {

        try {
            List<Long> allIds = questionRepository.findAllIds(); // Custom query that returns all question IDs

            if (allIds.size() < quantity) {
                throw new RuntimeException("Not enough questions available in the database.");
            }

            Collections.shuffle(allIds);
            List<Long> selectedIds = allIds.subList(0, quantity);

            List<Question> results = new ArrayList<>();
            for (Long id : selectedIds) {
                Question question = questionRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
                results.add(question);
            }

            return results;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<QuestionDTO> getRandomQuestionDTOs(int quantity) {

        try {
            List<Long> allIds = questionRepository.findAllIds(); // Custom query that returns all question IDs

            if (allIds.size() < quantity) {
                throw new RuntimeException("Not enough questions available in the database.");
            }

            Collections.shuffle(allIds);
            List<Long> selectedIds = allIds.subList(0, quantity);

            List<QuestionDTO> results = new ArrayList<>();
            for (Long id : selectedIds) {
                Question question = questionRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
                results.add(question.toDTO());
            }

            return results;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }



    public List<Question> saveAllQuestions(List<Question> questionsToSave) {
        return questionRepository.saveAll(questionsToSave);
    }
}
