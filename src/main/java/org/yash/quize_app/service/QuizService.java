package org.yash.quize_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yash.quize_app.dto.QuizRequest;
import org.yash.quize_app.entity.*;
import org.yash.quize_app.repository.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizQuestionRepository quizQuestionRepository;

    @Autowired
    private UserRepository userRepository;

    public Quiz createQuiz(QuizRequest request, String email) {

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Quiz quiz = new Quiz();
        quiz.setTopic(topic);
        quiz.setCreatedBy(user);
        quiz.setDuration(request.getDuration());
        quiz.setScheduledDate(request.getScheduledDate());
        quiz.setStartTime(request.getStartTime());
        quiz.setEndTime(request.getEndTime());

        Quiz savedQuiz = quizRepository.save(quiz);

        // 🔥 Link questions
        for (Long qId : request.getQuestionIds()) {
            Question question = questionRepository.findById(qId)
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            QuizQuestion qq = new QuizQuestion();
            qq.setQuiz(savedQuiz);
            qq.setQuestion(question);

            quizQuestionRepository.save(qq);
        }

        return savedQuiz;
    }
}