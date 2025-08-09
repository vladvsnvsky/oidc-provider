package com.myoidc.auth_server.services;

import com.myoidc.auth_server.dto.ExamDTO;
import com.myoidc.auth_server.models.Exam;
import com.myoidc.auth_server.models.ExamQuestion;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.models.UserEntity;
import com.myoidc.auth_server.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    private final QuestionService questionService;
    private final UserService userService;

    public ExamService(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }


    public ExamDTO create(String email){
        try{
            UserEntity userFetched = userService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

            Exam e = new Exam();
            e.setUser(userFetched);
            List<Question> fetchedQuestions = questionService.getRandomQuestions(10);
            Set<ExamQuestion> examQuestionSet = new HashSet<>();

            fetchedQuestions.forEach((q)->{
                ExamQuestion examQuestion = new ExamQuestion();
                examQuestion.setExam(e);
                examQuestion.setQuestion(q);
                examQuestion.setAnswered(false);
                examQuestionSet.add(examQuestion);
            });
            e.setQuestions(examQuestionSet);
            Exam eInDb = examRepository.save(e);
            return eInDb.toDTO();
        }catch(UsernameNotFoundException e){
            System.err.println("SERVICE Layer: User not found!");
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e){
            System.err.println("SERVICE LAYER:" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }




}
