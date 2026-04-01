package org.yash.quize_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.quize_app.entity.Answer;
import org.yash.quize_app.entity.Question;
import org.yash.quize_app.entity.Topic;
import org.yash.quize_app.repository.QuestionRepository;
import org.yash.quize_app.repository.TopicRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TopicRepository topicRepository;


    public Question createQuestion(Long topicId,Question question) {

        Topic topic = topicRepository.findById(topicId).orElseThrow(()->new RuntimeException("Topic not Found"));

        question.setTopic(topic);

        for(Answer ans : question.getAnswers()){ans.setQuestion(question);}

        return questionRepository.save(question);
    }
}
