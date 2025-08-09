package com.myoidc.auth_server.repositories;

import com.myoidc.auth_server.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q.id FROM Question q")
    List<Long> findAllIds();


}
