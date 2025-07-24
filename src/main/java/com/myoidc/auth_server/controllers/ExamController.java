package com.myoidc.auth_server.controllers;

import com.myoidc.auth_server.dto.AnswerDTO;
import com.myoidc.auth_server.dto.ExamDTO;
import com.myoidc.auth_server.dto.ExamResultDTO;
import com.myoidc.auth_server.dto.ExamSubmissionDTO;
import com.myoidc.auth_server.models.ApiResponse;
import com.myoidc.auth_server.models.Exam;
import com.myoidc.auth_server.models.ExamBuilder;
import com.myoidc.auth_server.models.Question;
import com.myoidc.auth_server.services.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;
import java.util.stream.Collectors;


@Controller("/")
@SessionAttributes("exam")
public class ExamController {

    private final QuestionService questionService;

    public ExamController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @PostMapping("/start-exam")
    public String startExam(@RequestParam("studentId") String studentId, Model model) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return "redirect:/?error=missingId";
        }

        try {
            Exam exam = ExamBuilder.buildExam(studentId);
            ExamDTO examDTO = ExamDTO.fromExam(exam);
            model.addAttribute("exam", examDTO);
            return "exam-page";
        } catch (Exception e) {
            model.addAttribute("message", "Failed to load questions.");
            return "error";
        }
    }

    /**
     * @param body : {
     *             "examId": "test",
     *             "studentId": "abc",
     *             "answers": [
     *             {
     *             "questionId": "q1",
     *             "selectedOptions": [
     *             "b"
     *             ]
     *             },
     *             {
     *             "questionId": "q2",
     *             "selectedOptions":[
     *             "a",
     *             "b",
     *             "d"]
     *             }, //this is the answer for question[1]
     *             {
     *             "questionId": "q3",
     *             "selectedOptions":["b"]
     *             } //this is the answer for question[2]
     *             ]
     *             }
     * @return
     */
//    @PostMapping("/submit-exam")
//    public ResponseEntity<ApiResponse<ExamResultDTO>> submitExam(
//            @RequestBody ExamSubmissionDTO body) {
//        try {
//            Exam e = ExamBuilder.buildExam("abc");
//
//            List<Question> examQuestion = e.getQuestions();
//            List<AnswerDTO> receivedAnswers = body.getAnswers();
//
//            Set<String> questionsIds = receivedAnswers.stream()
//                    .map(AnswerDTO::getQuestionId)
//                    .collect(Collectors.toSet());
//
//            List<Question> questions = questionService.getQuestionsById(questionsIds);
//
//            int count = 0;
//            for (AnswerDTO ra : receivedAnswers) {
//                Question q = questions.stream()
//                        .filter(question -> question.getId().equals(ra.getQuestionId()))
//                        .findFirst()
//                        .orElseThrow(() -> new Exception("Question not found for an answer!"));
//
//                if (q.check(ra.getSelectedOptions())) {
//                    count++;
//                }
//            }
//
//            int total = questions.size();
//            boolean passed = count > total / 2;
//            double score = ((int) (((double) count / total) * 10000)) / 100.0; // 2 decimal precision
//
//            ExamResultDTO result = new ExamResultDTO(passed, score, e.getId(), e.getStudentId());
//            return ResponseEntity.ok(ApiResponse.success(result));
//
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(ApiResponse.error("Submission failed: " + ex.getMessage()));
//        }
//    }



}

