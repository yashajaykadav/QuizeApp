package org.yash.quize_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.quize_app.entity.Question;
import org.yash.quize_app.entity.Topic;
import org.yash.quize_app.repository.QuestionRepository;
import org.yash.quize_app.repository.TopicRepository;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TopicRepository topicRepository;


    public Question createQuestion(Long topicId, Question question) {

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        // 🔥 VALIDATION (VERY IMPORTANT)
        if (!List.of("A", "B", "C", "D").contains(question.getCorrectAnswer())) {
            throw new RuntimeException("Correct answer must be A, B, C or D");
        }

        question.setTopic(topic);

        return questionRepository.save(question);
    }

    public List<Question> getQuestionsByTopic(Long topicId) {
        return questionRepository.findByTopicId(topicId);
    }
}
