package org.yash.quize_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.quize_app.entity.Subject;
import org.yash.quize_app.entity.Topic;
import org.yash.quize_app.repository.SubjectRepository;
import org.yash.quize_app.repository.TopicRepository;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository  topicRepository;

    @Autowired
    private SubjectRepository  subjectRepository;

    public Topic createTopic(Long SubjectId,Topic topic){
        Subject subject = subjectRepository.findById(SubjectId)
                .orElseThrow(()->new RuntimeException("Subject Not found"));

        topic.setSubject(subject);
        return topicRepository.save(topic);
    }

    public List<Topic>getAllTopic(){
        return topicRepository.findAll();
    }
}
