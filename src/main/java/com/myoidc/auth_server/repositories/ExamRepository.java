package com.myoidc.auth_server.repositories;

import com.myoidc.auth_server.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
