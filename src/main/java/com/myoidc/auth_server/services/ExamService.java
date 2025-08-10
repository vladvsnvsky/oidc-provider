package com.myoidc.auth_server.services;

import com.myoidc.auth_server.custom_exceptions.NoExamAttemptsException;
import com.myoidc.auth_server.dto.ExamDTO;
import com.myoidc.auth_server.dto.UserUpdateDTO;
import com.myoidc.auth_server.models.Exam;
import com.myoidc.auth_server.models.ExamQuestion;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.models.UserEntity;
import com.myoidc.auth_server.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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


    @Transactional
    public ExamDTO create(String email){
        try{
            UserEntity userFetched = userService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

            if(userFetched.getAttempts() < 1)
                throw new NoExamAttemptsException();

            if(userHasActiveExam(userFetched.getId()))
            {
                throw new RuntimeException("User already has an active exam!");
            }

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
            UserUpdateDTO userUpdate = new UserUpdateDTO();
            userUpdate.setAttempts(userFetched.getAttempts()-1);
            //this must be like one transaction
            Exam eInDb = examRepository.save(e);
            userService.update(userFetched.getId(),userUpdate);

            return eInDb.toDTO();
        }catch (NoExamAttemptsException e){
            System.err.println("SERVICE Layer: No attempts for this user!");
            throw new RuntimeException("No attempts for this user!");
        }
        catch(UsernameNotFoundException e){
            System.err.println("SERVICE Layer: User not found!");
            throw new RuntimeException(e.getMessage());
        }
        catch (Exception e){
            System.err.println("SERVICE LAYER:" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    private boolean userHasActiveExam(UUID id) {
        return examRepository.existsByUserIdAndFinishedFalse(id);
    }

    /**
     * This will return the exam_ids for the user's exams
     * @param username
     * @param pageable
     * @param query
     * @return
     */
    public Page<Long> getByUsername(String username, Pageable pageable, String query) {
            UserEntity userFetched = userService.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

            if (query.isBlank()) {
                return examRepository.findByUserId(userFetched.getId(),pageable)
                        .map(Exam::getId);
            } else {
                // adapt the search to whatever field you want to filter on
                return examRepository.searchByUserAndQuery(userFetched.getId(), query.toLowerCase(), pageable)
                        .map(Exam::getId);
            }
    }
}
