package org.yash.quize_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yash.quize_app.entity.Answer;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // Find all answers for an attempt
    List<Answer> findByAttemptId(Long attemptId);

    // Find answer for a specific question in an attempt
    Optional<Answer> findByAttemptIdAndQuestionId(Long attemptId, Long questionId);
}
