package com.myoidc.auth_server.repositories;

import com.myoidc.auth_server.models.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Page<Exam> findByUserId(UUID userId, Pageable pageable);

    // Example search: by question text or category (join through ExamQuestion)
    @Query("""
        select distinct e
        from Exam e
        join e.questions eq
        join eq.question q
        where e.user.id = :userId
          and (lower(q.text) like concat('%', :q, '%')
               or lower(q.category) like concat('%', :q, '%'))
        """)
    Page<Exam> searchByUserAndQuery(@Param("userId") UUID userId,
                                    @Param("q") String q,
                                    Pageable pageable);

    boolean existsByUserIdAndFinishedFalse(UUID userId);

    // or if you don’t have createdAt:
    Optional<Exam> findFirstByUserIdAndFinishedFalse(UUID userId);
}
