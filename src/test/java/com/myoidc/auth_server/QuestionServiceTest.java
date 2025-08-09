package com.myoidc.auth_server;

import com.myoidc.auth_server.dto.QuestionDTO;
import com.myoidc.auth_server.services.QuestionService;

import java.util.List;

public class QuestionServiceTest {
    public static void main(String[] args) {
        QuestionService service = new QuestionService();
        List<QuestionDTO> ql = service.getRandomQuestionDTOs(5);
        System.out.println(ql);
    }
}
