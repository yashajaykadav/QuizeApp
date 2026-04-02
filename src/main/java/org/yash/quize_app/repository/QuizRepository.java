package org.yash.quize_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yash.quize_app.entity.Quiz;

import java.time.LocalDate;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findByScheduledDate(LocalDate date);
}
