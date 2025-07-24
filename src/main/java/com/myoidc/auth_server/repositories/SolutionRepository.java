package com.myoidc.auth_server.repositories;

import com.myoidc.auth_server.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long> {}
