package org.yash.quize_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yash.quize_app.entity.QuizQuestion;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Long> {
    List<QuizQuestion> findByQuizId(Long quizId);
}
