package com.myoidc.auth_server.services;

import com.myoidc.auth_server.custom_exceptions.ExamNotFoundException;
import com.myoidc.auth_server.custom_exceptions.NoExamAttemptsException;
import com.myoidc.auth_server.dto.ExamDTO;
import com.myoidc.auth_server.dto.RegisterAnswerDTO;
import com.myoidc.auth_server.dto.ResponseModelDTO;
import com.myoidc.auth_server.dto.UserUpdateDTO;
import com.myoidc.auth_server.models.*;
import com.myoidc.auth_server.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
            UserEntity fUser = getUserEntityByEmail(email);

            if(fUser.getAttempts() < 1)
                throw new NoExamAttemptsException();

            if(userHasActiveExam(fUser.getId()))
            {
                throw new RuntimeException("User already has an active exam!");
            }

            Exam e = new Exam();
            e.setUser(fUser);
            List<Question> fQuestions = questionService.getRandomQuestions(10);
            Set<ExamQuestion> examQuestionSet = new HashSet<>();

            fQuestions.forEach((q)->{
                ExamQuestion examQuestion = new ExamQuestion();
                examQuestion.setExam(e);
                examQuestion.setQuestion(q);
                examQuestion.setAnswered(false);
                examQuestionSet.add(examQuestion);
            });
            e.setQuestions(examQuestionSet);
            UserUpdateDTO userUpdate = new UserUpdateDTO();
            userUpdate.setAttempts(fUser.getAttempts()-1);
            //this must be like one transaction
            Exam fExam = examRepository.save(e);
            userService.update(fUser.getId(),userUpdate);

            return fExam.toDTO();
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
    public Page<Long> getExamsByUsername(String username, Pageable pageable, String query) {
            UserEntity fUser = getUserEntityByEmail(username);

            if (query.isBlank()) {
                return examRepository.findByUserId(fUser.getId(),pageable)
                        .map(Exam::getId);
            } else {
                // adapt the search to whatever field you want to filter on
                return examRepository.searchByUserAndQuery(fUser.getId(), query.toLowerCase(), pageable)
                        .map(Exam::getId);
            }
    }

    UserEntity getUserEntityByEmail(String username){
        return userService.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    public ExamDTO getActiveExam(String username) {
        UserEntity user = getUserEntityByEmail(username);
        Exam e = examRepository.findFirstByUserIdAndFinishedFalse(user.getId()).orElseThrow(()->new ExamNotFoundException("You don't have an active exam!"));
        return e.toDTO();
    }

    public Object recordAnswer(String username, RegisterAnswerDTO dto) {
        try {
            //get user by username/mail
            UserEntity fUser = getUserEntityByEmail(username);
            //get userId
            UUID userId = fUser.getId();
            //get exam by userId
            Exam fExam = examRepository.findFirstByUserIdAndFinishedFalse(userId).orElseThrow(()->new RuntimeException("No active exams for this user"));

            //get examQuestion from db
            ExamQuestion fExamQuestion = fExam.getQuestions().stream().filter(q -> q.getId() == dto.getExamQuestion()).findFirst()
                    .orElseThrow(()->new RuntimeException("Exam Question not found!"));

            //check if exam has an active answer already
            Answer fAnswer = fExamQuestion.getAnswer();
            boolean mustCreateNewAnswer = false;
            if(fAnswer==null)
            {
                fAnswer = new Answer();
                mustCreateNewAnswer = true;
            }

            fAnswer.setExamQuestion(fExamQuestion);

            //TODO: COntinue here
            List<ResponseModelDTO> responseModelDTOS = dto.getResponses();
            List<ResponseModel> responses = new ArrayList<>();
//            responseModelDTOS.forEach((rdto) ->{
//                ResponseModel r = new ResponseModel();
//                r.setChecked(rdto.isChecked());
//                r.setOptionId(rdto.getOption());
//                r.setAnswer(fAnswer);
//                responses.add(r);
//            });

            int dtosSize = responseModelDTOS.size();
            for(int i=0; i<dtosSize; i++){
                ResponseModel r = new ResponseModel();
                ResponseModelDTO rDTO = responseModelDTOS.get(i);
                r.setChecked(rDTO.isChecked());
                r.setOptionId(rDTO.getOption());
                r.setAnswer(fAnswer);
                responses.add(r);
            }

            fAnswer.setResponses(responses);
            fExamQuestion.setAnswer(fAnswer);
            fExam.updateExamQuestion(fExamQuestion);
            examRepository.save(fExam);

            throw new RuntimeException("Logic to register answer not yet implemented!");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
