package org.yash.quize_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yash.quize_app.entity.Attempt;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
    // Find all attempts by student
    List<Attempt> findByStudentId(Long studentId);

    // Check if student already attempted a quiz (prevent re-attempt)
    Optional<Attempt> findByStudentIdAndQuizId(Long studentId, Long quizId);
}
