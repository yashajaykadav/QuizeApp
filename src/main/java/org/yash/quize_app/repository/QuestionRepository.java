package org.yash.quize_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yash.quize_app.entity.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByTopicId(Long topicId);
}
